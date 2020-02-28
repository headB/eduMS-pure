/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */

layui.define(["table", "form", "layer", "common"], function (e) {

    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        common = layui.common,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-user-manage",
        url: "/employees",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "username", title: "用户名", width: 135},
            {field: "trueName", title: "姓名"},
            {field: "deptName", title: "部门"},
            {field: "duty", title: "职称"},
            {field: "tel", title: "联系方式", width: 120},
            {field: "sex", title: "性别"},
            {
                field: "types", title: "状态", templet: function (row) {
                    return common.genYesOrNoTag(row.status < 0, '停用', '正常');
                }
            },
            {field: "enterTime", title: "加入时间"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#table-useradmin-webuser"}
        ]],
        toolbar: '#table-emp-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-user-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-user-manage)", function (e) {
        active[e.event](e);
    }), e("employee", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-user-manage'),
                checkData = checkStatus.data,//得到选中的数据
                stoped = false;// 是否有已经停用的员工

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            $.each(checkData, function (i, v) {
                if (v.status < 0) {
                    stoped = true;
                    return;
                }
            });

            if (stoped) {
                lay.msg('不能选择已被停用的数据');
                return;
            }

            layer.confirm('确定将这些员工设置为停用状态吗？', function (index) {
                var ids = $.map(checkData, function (v) {
                    return v.id
                });
                // 执行 Ajax 后重载
                admin.req({
                    url: '/employees',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                        if (e.code === 0) {
                            setTimeout(function () {
                                layui.table.reload("LAY-user-manage"), layer.close(index)
                            }, 1000);
                        }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加用户',
                area: ["960px", "480px"],
                id: 'LAY-popup-user-add',
                success: function (layero, index) {
                    view(this.id).render('system/employee/form').done(function () {
                        // 渲染部门列表
                        $("#f_deptId").html("<option value='-1'>请选择</option>" + deptOptions);

                        form.render(null, 'layadmin-emp-formlist');

                        //监听提交
                        form.on('submit(LAY-emp-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/employees',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-user-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        }, "resetpwd": function (e) {
            var checkStatus = table.checkStatus('LAY-user-manage')
                , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定要重置这些员工的密码吗？', function (index) {
                var ids = $.map(checkData, function (v) {
                    return v.id
                });

                // 提交数据到后台
                admin.req({
                    url: '/employees/resetpwd',
                    type: 'patch',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg), layer.close(index);
                    }
                });
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑用户",
                area: ["960px", "480px"],
                id: "LAY-popup-user-add",
                success: function (e, i) {
                    view(this.id).render("system/employee/form", data).done(function () {
                        // 渲染部门列表
                        $("#f_deptId").html("<option value='-1'>请选择</option>" + deptOptions);
                        // 数据回显
                        form.val("layadmin-emp-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-emp-formlist");

                        // 绑定提交表单事件
                        form.on("submit(LAY-emp-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/employees',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-user-manage"), layer.close(i)
                                        }, 1000);
                                    }
                                }
                            });
                            return false;
                        })
                    });
                }
            });
        }, "del": function (e) {
            layer.confirm("确定要将该员工状态设置为停用吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/employees',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg)
                        }
                    })
                }
                layui.table.reload("LAY-user-manage"), layer.close(i)
            });
        }, "restore": function (e) {
            layer.confirm("确定将该员工状态恢复为正常吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/employees/' + e.data.id + '/restore',
                        type: 'patch',
                        done: function (res) {
                            layer.msg(res.msg)
                        }
                    })
                }
                layui.table.reload("LAY-user-manage"), layer.close(i)
            });
        }
    };
});