/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-teacherClass-manage",
        url: "/teacherClasss",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "empName", title: "名字"},
            {field: "subjectName", title: "学科"},
            {field: "statusName", title: "状态"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#list-manage-btn"}
        ]],
        toolbar: '#table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 680,
        text: "对不起，加载出现异常！"
    }), table.on("toolbar(LAY-teacherClass-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-teacherClass-manage)", function (e) {
        active[e.event](e);
    }), e("teacherClass", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-teacherClass-manage')
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
                    url: '/teacherClasss',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-teacherClass-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        },
        "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '550px'],
                id: 'LAY-popup-teacherClass-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/teacherClass/form').done(function () {
                        form.render(null, 'layadmin-teacherClass-formlist');

                        //监听提交
                        form.on('submit(LAY-teacherClass-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/teacherClasss',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-teacherClass-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        },
        "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ["500px", "550px"],
                id: "LAY-popup-teacherClass-add",
                success: function (e, i) {
                    view(this.id).render("teaching/teacherClass/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-teacherClass-formlist", data);
                        // 禁用用户名表单
                        $("input[name=username]").prop("disabled", true).prop("readOnly", true);
                        // 重新渲染页面
                        form.render(null, "layadmin-teacherClass-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-teacherClass-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/teacherClasss',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-teacherClass-manage"), layer.close(i)
                                    }, 1000);
                                }
                                }
                            });
                            return false;
                        });
                    });
                }
            });

            setTimeout(function () {
                // 为什么我的表单是如何多多灾多难的！！！终于搞定了。添加了延时加载！
                form.val("layadmin-teacherClass-formlist", data);
            },100)

        },
        "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/teacherClasss',
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