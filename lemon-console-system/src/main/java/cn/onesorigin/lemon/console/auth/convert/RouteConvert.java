package cn.onesorigin.lemon.console.auth.convert;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.map.MapUtil;
import cn.onesorigin.lemon.console.auth.model.resp.RouteResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 路由 转换器
 *
 * @author BruceMaa
 * @since 2025-09-25 09:42
 */
@Mapper
public interface RouteConvert {

    RouteConvert INSTANCE = Mappers.getMapper(RouteConvert.class);

    default RouteResp convert(Tree<Long> tree) {
        if (tree == null) {
            return null;
        }
        var routeResp = new RouteResp();

        routeResp.setId(tree.getId());
        routeResp.setParentId(tree.getParentId());
        routeResp.setTitle(Convert.toStr(tree.getName()));
        routeResp.setSort(Convert.toInt(tree.getWeight()));
        routeResp.setType(MapUtil.getInt(tree, "type"));
        routeResp.setPath(MapUtil.getStr(tree, "path"));
        routeResp.setName(MapUtil.getStr(tree, "name"));
        routeResp.setComponent(MapUtil.getStr(tree, "component"));
        routeResp.setRedirect(MapUtil.getStr(tree, "redirect"));
        routeResp.setIcon(MapUtil.getStr(tree, "icon"));
        routeResp.setIsExternal(MapUtil.getBool(tree, "isExternal"));
        routeResp.setIsCache(MapUtil.getBool(tree, "isCache"));
        routeResp.setIsHidden(MapUtil.getBool(tree, "isHidden"));
        routeResp.setPermission(MapUtil.getStr(tree, "permission"));

        // 处理子节点
        List<Tree<Long>> children = tree.getChildren();
        if (children != null && !children.isEmpty()) {
            routeResp.setChildren(convert(children));
        }
        return routeResp;
    }

    default List<RouteResp> convert(List<Tree<Long>> trees) {
        if (trees == null) {
            return null;
        }
        return trees.stream().map(this::convert).toList();
    }
}
