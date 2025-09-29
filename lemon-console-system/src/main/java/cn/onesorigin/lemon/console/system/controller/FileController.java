package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.FileQuery;
import cn.onesorigin.lemon.console.system.model.req.FileReq;
import cn.onesorigin.lemon.console.system.model.resp.FileResp;
import cn.onesorigin.lemon.console.system.model.resp.FileStatisticsResp;
import cn.onesorigin.lemon.console.system.model.resp.FileUploadResp;
import cn.onesorigin.lemon.console.system.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

import java.io.IOException;

/**
 * 文件管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-29 11:03
 */
@Tag(name = "文件管理")
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@CrudRequestMapping(value = "/system/files", api = {
        Api.PAGE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class FileController extends BaseController<FileService, FileResp, FileResp, FileQuery, FileReq> {

    @Operation(summary = "上传文件", description = "上传文件")
    @Parameter(name = "parentPath", description = "上级目录（默认：/yyyy/MM/dd）", example = "/", in = ParameterIn.QUERY)
    @SaCheckPermission("system:files:upload")
    @PostMapping("/upload")
    public FileUploadResp upload(@NotNull(message = "文件不能为空") @RequestPart MultipartFile file,
                                 @RequestParam(required = false) String parentPath) throws IOException {
        ValidationUtils.throwIf(file::isEmpty, "文件不能为空");
        FileInfo fileInfo = baseService.upload(file, parentPath);
        return FileUploadResp.builder()
                .id(fileInfo.getId())
                .url(fileInfo.getUrl())
                .thUrl(fileInfo.getThUrl())
                .metadata(fileInfo.getMetadata())
                .build();
    }

    @Operation(summary = "创建文件夹", description = "创建文件夹")
    @SaCheckPermission("system:files:createDir")
    @PostMapping("/dir")
    public Long createDir(@RequestBody @Valid FileReq req) {
        ValidationUtils.throwIfBlank(req.getParentPath(), "上级目录不能为空");
        return baseService.createDir(req);
    }

    @Operation(summary = "计算文件夹大小", description = "计算文件夹大小")
    @SaCheckPermission("system:files:calcDirSize")
    @GetMapping("/dir/{id}/size")
    public Long calcDirSize(@PathVariable Long id) {
        return baseService.calcDirSize(id);
    }

    @Operation(summary = "查询文件资源统计", description = "查询文件资源统计")
    @SaCheckPermission("system:files:list")
    @GetMapping("/statistics")
    public FileStatisticsResp statistics() {
        return baseService.statistics();
    }

    @Operation(summary = "检测文件是否存在", description = "检测文件是否存在")
    @SaCheckPermission("system:files:check")
    @GetMapping("/check")
    public FileResp checkFile(String fileHash) {
        return baseService.check(fileHash);
    }
}
