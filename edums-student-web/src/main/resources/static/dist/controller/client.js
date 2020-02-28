/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-client-manage",
        url: "/clients",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "vdate", title: "vdate"},
            {field: "lastcontacttime", title: "lastcontacttime"},
            {field: "truename", title: "truename"},
            {field: "qq", title: "qq"},
            {field: "tel", title: "tel"},
            {field: "age", title: "age"},
            {field: "sex", title: "sex"},
            {field: "email", title: "email"},
            {field: "address", title: "address"},
            {field: "school", title: "school"},
            {field: "edu", title: "edu"},
            {field: "major", title: "major"},
            {field: "workyear", title: "workyear"},
            {field: "introducername", title: "introducername"},
            {field: "focus", title: "focus"},
            {field: "remark", title: "remark"},
            {field: "status", title: "status"},
            {field: "lostdate", title: "lostdate"},
            {field: "lostreason", title: "lostreason"},
            {field: "lostinfo", title: "lostinfo"},
            {field: "telnum", title: "telnum"},
            {field: "imnum", title: "imnum"},
            {field: "visitnum", title: "visitnum"},
            {field: "othernum", title: "othernum"},
            {field: "types", title: "types"},
            {field: "applyzero", title: "applyzero"},
            {field: "applyprogress", title: "applyprogress"},
            {field: "inputdate", title: "inputdate"},
            {field: "nextvisitdate", title: "nextvisitdate"},
            {field: "ordercomedate", title: "ordercomedate"},
            {field: "lastupdatetime", title: "lastupdatetime"},
            {field: "haslaptop", title: "haslaptop"},
            {field: "startschool", title: "startschool"},
            {field: "collegeclass", title: "collegeclass"},
            {field: "weixinid", title: "weixinid"},
            {field: "weixinid2", title: "weixinid2"},
            {field: "examnumber", title: "examnumber"},
            {field: "existence", title: "existence"},
            {field: "topooltime", title: "topooltime"},
            {field: "frompooltime", title: "frompooltime"},
            {field: "subjectId", title: "subjectId"},
            {field: "scId", title: "scId"},
            {field: "intentclassId", title: "intentclassId"},
            {field: "introducerId", title: "introducerId"},
            {field: "inputuserId", title: "inputuserId"},
            {field: "provinceId", title: "provinceId"},
            {field: "currentstatusId", title: "currentstatusId"},
            {field: "sourceId", title: "sourceId"},
            {field: "sellerId", title: "sellerId"},
            {field: "intentlevelId", title: "intentlevelId"},
            {field: "weixinnumber", title: "weixinnumber"},
            {field: "campusId", title: "campusId"},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#client-list-manage-btn"}
        ]],
        toolbar: '#client-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-client-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-client-manage)", function (e) {
        active[e.event](e);
    }), e("client", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-client-manage')
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
                    url: '/clients',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-client-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-client-add',
                success: function (layero, index) {
                    view(this.id).render('system/client/form').done(function () {
                        form.render(null, 'layadmin-client-formlist');

                        //监听提交
                        form.on('submit(LAY-client-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/clients',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-client-manage"), layer.close(index)
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
                id: "LAY-popup-client-add",
                success: function (e, i) {
                    view(this.id).render("system/client/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-client-formlist", data);
                        // 重新渲染页面
                        form.render(null, "layadmin-client-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-client-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/clients',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-client-manage"), layer.close(i)
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
                        url: '/clients',
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