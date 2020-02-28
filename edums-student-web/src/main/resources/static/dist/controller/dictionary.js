/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-dictionary-manage",
        // url: "/dictionary/details",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {field: "sequence", title: "序号"},
            {field: "title", title: "标题"},
            {field: "tvalue", title: "内容"},
            {field: "intro", title: "简介"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#dictionary-list-manage-btn"}
        ]],
        toolbar: '#dictionary-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' // 标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: false,
        height: 'full-100',
    }), table.on("toolbar(LAY-dictionary-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-dictionary-manage)", function (e) {
        active[e.event](e);
    }), e("dictionary", {});

    //事件
    var active = {
        "delDic": function () {
            var dic = $($("#edums-dictionary-side .layui-this")[0]),
                data = dic.data('json');
            if (!data || !data.id) {
                layer.msg("数据异常");
                return;
            }

            if (table.cache['LAY-dictionary-manage'].length > 0) {
                var first = table.cache['LAY-dictionary-manage'][0];
                if (!(first instanceof Array && first.length === 0)) {
                    layer.msg("当前字典还有详情，无法删除");
                    return;
                }
            }

            layer.confirm('确定删除该字典目录吗？', function (index) {
                //执行 Ajax 后重载
                admin.req({
                    url: '/dictionary',
                    type: 'delete',
                    data: {ids: [data.id]},
                    done: function (e) {
                        layer.msg(e.msg);
                        if (e.code === 0) {
                            setTimeout(function () {
                                layui.index.render(), layer.close(index)
                            }, 500);
                        }
                    }
                });
            });
        }, "createDic": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '360px'],
                id: 'LAY-popup-dictionary-add',
                success: function (layero, index) {
                    view(this.id).render('system/dictionary/dic').done(function () {
                        form.render(null, 'layadmin-dictionary-formlist');

                        //监听提交
                        form.on('submit(LAY-dictionary-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/dictionary',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.index.render(), layer.close(index)
                                        }, 500);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        }, "editDic": function (e) {
            admin.popup({
                title: "编辑",
                area: ['500px', '400px'],
                id: "LAY-popup-dictionary-edit",
                success: function (e, i) {
                    view(this.id).render("system/dictionary/dic").done(function () {
                        var dic = $($("#edums-dictionary-side .layui-this")[0]),
                            data = dic.data('json');
                        // 数据回显
                        form.val("layadmin-dictionary-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-dictionary-formlist"),
                            // 绑定提交表单事件
                            form.on("submit(LAY-dictionary-front-submit)", function (e) {
                                // 提交数据到后台
                                admin.req({
                                    url: '/dictionary',
                                    type: 'put',
                                    data: e.field,
                                    done: function (e) {
                                        layer.msg(e.msg);
                                        if (e.code === 0) {
                                            setTimeout(function () {
                                                location.reload(), layer.close(i)
                                            }, 1000);
                                        }
                                    }
                                });
                                return false;
                            });
                    });
                }
            });
        }, "addDetail": function () {
            admin.popup({
                title: '添加',
                area: ['500px', '400px'],
                id: 'LAY-popup-dictionary-add',
                success: function (layero, index) {
                    view(this.id).render('system/dictionary/detail').done(function () {
                        var dic = $($("#edums-dictionary-side .layui-this")[0]),
                            json = dic.data('json');
                        // 数据回显
                        form.val("layadmin-dictionary-formlist", {parentId: json.id, parentName: json.title});
                        form.render(null, 'layadmin-dictionary-formlist');

                        //监听提交
                        form.on('submit(LAY-dictionary-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/dictionary/details',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-dictionary-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        }, "editDetail": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['500px', '400px'],
                id: "LAY-popup-dictionary-edit",
                success: function (e, i) {
                    view(this.id).render("system/dictionary/detail", data).done(function () {
                        // 数据回显
                        form.val("layadmin-dictionary-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-dictionary-formlist"),
                            // 绑定提交表单事件
                            form.on("submit(LAY-dictionary-front-submit)", function (e) {
                                // 提交数据到后台
                                admin.req({
                                    url: '/dictionary/details',
                                    type: 'put',
                                    data: e.field,
                                    done: function (e) {
                                        layer.msg(e.msg);
                                        if (e.code === 0) {
                                            setTimeout(function () {
                                                layui.table.reload("LAY-dictionary-manage"), layer.close(i)
                                            }, 1000);
                                        }
                                    }
                                });
                                return false;
                            });
                    });
                }
            });
        }, "delDetail": function (e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/dictionary/details',
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