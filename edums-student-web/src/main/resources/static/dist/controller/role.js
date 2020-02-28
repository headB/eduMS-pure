/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-role-manage",
        url: "/roles",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "name", title: "编码"},
            {field: "path", title: "路径"},
            {field: "title", title: "名称"},
            {field: "description", title: "描述"},
            {field: "status", title: "状态"},
            {field: "types", title: "类型"},
            {field: "parentId", title: "父角色"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#role-list-manage-btn"}
        ]],
        toolbar: '#role-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-role-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-role-manage)", function (e) {
        active[e.event](e);
    }), e("role", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-role-manage')
            , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定删除吗？', function (index) {
                var ids = $.map(checkData, function (v, i) {
                    return v.id
                });
                //执行 Ajax 后重载
                admin.req({
                    url: '/roles',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-role-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-role-add',
                success: function (layero, index) {
                    view(this.id).render('system/role/form').done(function () {
                        form.render(null, 'layadmin-role-formlist');

                        //监听提交
                        form.on('submit(LAY-role-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/roles',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-role-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['960px', '480px'],
                id: "LAY-popup-role-add",
                success: function (e, i) {
                    view(this.id).render("system/role/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-role-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-role-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-role-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/roles',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-role-manage"), layer.close(i)
                                    }, 1000);
                                }
                                }
                            });
                            return false;
                        });
                    });
                }
            });
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/roles',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        }
    };
});