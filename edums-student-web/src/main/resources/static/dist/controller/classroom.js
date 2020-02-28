/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer","common"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        common = layui.common,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-classroom-manage",
        url: "/classrooms",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "name", title: "教室名称"},
            {field: "address", title: "教室地址"},
            {field: "capacity", title: "容纳人数"},
            {field: "status", title: "教室状态",templet: function (row) {
                    return common.genYesOrNoTag(row.status < 0, '停用','正常');
                }},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#classroom-list-manage-btn"}
        ]],
        toolbar: '#classroom-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-classroom-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-classroom-manage)", function (e) {
        active[e.event](e);
    }), e("classroom", {});

    //事件
    var active = {
        "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '450px'],
                id: 'LAY-popup-classroom-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/classroom/form').done(function () {
                        form.render(null, 'layadmin-classroom-formlist');

                        //监听提交
                        form.on('submit(LAY-classroom-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/classrooms',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-classroom-manage"), layer.close(index)
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
                area: ["500px", "450px"],
                id: "LAY-popup-classroom-add",
                success: function (e, i) {
                    view(this.id).render("teaching/classroom/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-classroom-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-classroom-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-classroom-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/classrooms',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-classroom-manage"), layer.close(i)
                                    }, 1000);
                                }
                                }
                            });
                            return false;
                        });
                    });
                }
            });
        },  del:function (e) {
            layer.confirm("确定要禁用该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/classrooms',
                        data: {id: e.data.id},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                            if (res.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-classroom-manage"), layer.close(i)
                                }, 1000);
                            }
                        }
                    });
                }
                //e.del(), layer.close(i)
            });
        }
    };
});