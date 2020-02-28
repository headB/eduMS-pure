/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-student-manage",
        url: "/students",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "truename", title: "truename"},
            {field: "qq", title: "qq"},
            {field: "age", title: "age"},
            {field: "sex", title: "sex"},
            {field: "tel", title: "tel"},
            {field: "password", title: "password"},
            {field: "address", title: "address"},
            {field: "email", title: "email"},
            {field: "edu", title: "edu"},
            {field: "homeaddress", title: "homeaddress"},
            {field: "school", title: "school"},
            {field: "major", title: "major"},
            {field: "householdaddress", title: "householdaddress"},
            {field: "workyear", title: "workyear"},
            {field: "otherenglishlevel", title: "otherenglishlevel"},
            {field: "registdate", title: "registdate"},
            {field: "status", title: "status"},
            {field: "photo", title: "photo"},
            {field: "idcard", title: "idcard"},
            {field: "sostel", title: "sostel"},
            {field: "sosname", title: "sosname"},
            {field: "feemethod", title: "feemethod"},
            {field: "idcopy", title: "idcopy"},
            {field: "educopy", title: "educopy"},
            {field: "workintent", title: "workintent"},
            {field: "workexperi", title: "workexperi"},
            {field: "remark", title: "remark"},
            {field: "types", title: "types"},
            {field: "tuitionfee", title: "tuitionfee"},
            {field: "payment", title: "payment"},
            {field: "lastpaytime", title: "lastpaytime"},
            {field: "lastchargetime", title: "lastchargetime"},
            {field: "nextchargetime", title: "nextchargetime"},
            {field: "chargenum", title: "chargenum"},
            {field: "marketfeerate", title: "marketfeerate"},
            {field: "marketfee", title: "marketfee"},
            {field: "marketinitialfee", title: "marketinitialfee"},
            {field: "marketpayment", title: "marketpayment"},
            {field: "marketsettingtime", title: "marketsettingtime"},
            {field: "worktime", title: "worktime"},
            {field: "salary", title: "salary"},
            {field: "logintimes", title: "logintimes"},
            {field: "lastlogintime", title: "lastlogintime"},
            {field: "lastupdatetime", title: "lastupdatetime"},
            {field: "infoscore", title: "infoscore"},
            {field: "haslaptop", title: "haslaptop"},
            {field: "workintentjob", title: "workintentjob"},
            {field: "workproblem", title: "workproblem"},
            {field: "workremark", title: "workremark"},
            {field: "workintentaddr", title: "workintentaddr"},
            {field: "workintenthelp", title: "workintenthelp"},
            {field: "borrow", title: "borrow"},
            {field: "beginrepaytime", title: "beginrepaytime"},
            {field: "endrepaytime", title: "endrepaytime"},
            {field: "repayfinishtime", title: "repayfinishtime"},
            {field: "ispunctualrepay", title: "ispunctualrepay"},
            {field: "honest", title: "honest"},
            {field: "notified", title: "notified"},
            {field: "paystatus", title: "paystatus"},
            {field: "paystatuschangetime", title: "paystatuschangetime"},
            {field: "englishlevelId", title: "englishlevelId"},
            {field: "sourceId", title: "sourceId"},
            {field: "clientId", title: "clientId"},
            {field: "currentclassId", title: "currentclassId"},
            {field: "marketsettinguserId", title: "marketsettinguserId"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#student-list-manage-btn"}
        ]],
        toolbar: '#student-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-student-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-student-manage)", function (e) {
        active[e.event](e);
    }), e("student", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-student-manage')
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
                    url: '/students',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-student-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-student-add',
                success: function (layero, index) {
                    view(this.id).render('system/student/form').done(function () {
                        form.render(null, 'layadmin-student-formlist');

                        //监听提交
                        form.on('submit(LAY-student-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/students',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-student-manage"), layer.close(index)
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
                id: "LAY-popup-student-add",
                success: function (e, i) {
                    view(this.id).render("system/student/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-student-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-student-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-student-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/students',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-student-manage"), layer.close(i)
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
                        url: '/students',
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