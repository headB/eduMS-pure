/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-estimateOption-manage",
        url: "/estimateOptions",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "title", title: "title"},
            {field: "asscCombinId", title: "asscCombinId"},
            {field: "orderby", title: "orderby"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#estimateOption-list-manage-btn"}
        ]],
        toolbar: '#estimateOption-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-estimateOption-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-estimateOption-manage)", function (e) {
        active[e.event](e);
    }), e("estimateOption", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-estimateOption-manage')
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
                    url: '/estimateOptions',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-estimateOption-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {

            window.add = true;

            // addContentButton

            window.currentOptions = "";
            $("#addContent").html("");

            $(".layui-form-item dynamicContent").html("");

            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-estimateOption-add',
                success: function (layero, index) {
                    view(this.id).render('system/estimateOption/form').done(function () {
                        form.render(null, 'layadmin-estimateOption-formlist');

                        //监听提交
                        form.on('submit(LAY-estimateOption-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimateOptions',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-estimateOption-manage"), layer.close(index)
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
                id: "LAY-popup-estimateOption-add",
                success: function (e, i) {
                    view(this.id).render("system/estimateOption/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-estimateOption-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-estimateOption-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-estimateOption-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimateOptions',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-estimateOption-manage"), layer.close(i)
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
                        url: '/estimateOptions',
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