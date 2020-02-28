/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];

    table.render({
        elem: "#LAY-estimate-manage",
        url: "/estimates?investigation=false",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "teacherName", title: "评选人"},
            {field: "operatorName", title: "创建人", hide: true},
            {field: "url", title: "访问链接"},
            {field: "createTime", title: "创建时间"},
            {field: "expiredTime", title: "结束时间", hide: true},
            {field: "subjectName", title: "学科", hide: true},
            {field: "classIdName", title: "班级", hide: true},
            {field: "className", title: "班级名"},
            {field: "total", title: "参考评分人数"},
            {field: "remark", title: "备注", hide: true},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#estimate-list-manage-btn"}
        ]],
        toolbar: '#estimate-table-manage-btn',
        defaultToolbar: ['filter',  {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-estimate-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-estimate-manage)", function (e) {
        active[e.event](e);
    }), e("estimate", {});

    //事件
    var active = {

        "LAYTABLE_COLS": function (e) {

        },
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-estimate-manage')
                , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定要批量删除吗？', function (index) {
                var ids = $.map(checkData, function (v, i) {
                    return v.id
                });
                //执行 Ajax 后重载
                admin.req({
                    url: '/estimates',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                        if (e.code === 0) {
                            setTimeout(function () {
                                layui.table.reload("LAY-estimate-manage"), layer.close(index)
                            }, 1000);
                        }
                    }
                });
            });
        },
        "add": function (e) {
            window.processing=null;
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-estimate-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/estimate/form').done(function () {
                        form.render(null, 'layadmin-estimate-formlist');

                        //监听提交
                        form.on('submit(LAY-estimate-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimates',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-estimate-manage"), layer.close(index)
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
        edit: function (e) {
            var data = e.data;
            //console.log(data);
            admin.popup({
                title: "编辑",
                area: ["500px", "450px"],
                id: "LAY-popup-estimate-add",
                success: function (e, i) {

                    view(this.id).render("teaching/estimate/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-estimate-formlist", data);
                        // 禁用用户名表单
                        $("input[name=username]").prop("disabled", true).prop("readOnly", true);
                        // 重新渲染页面
                        form.render(null, "select");
                        // 绑定提交表单事件
                        form.on("submit(LAY-estimate-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimates',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-estimate-manage"), layer.close(i)
                                        }, 1000);
                                    }
                                }
                            });
                            return false;
                        });

                        setTimeout(function () {
                            // 为什么我的表单是如何多多灾多难的！！！终于搞定了。添加了延时加载！
                            form.val("layadmin-estimate-formlist", data);
                        }, 100)


                    });
                }
            });
        },
        "del": function (e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/estimates',
                        data: {ids: e.data.id},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        },
        "show": function (e) {

            //然后查询已经提交的数量。
            window.currentCustomId = e.data.id;
            // admin.req({
            //     url: '/estimateCommits?estimateId=' + e.data.id,
            //     type: 'get',
            //     done: function (e) {
            //         alert("目前提交的人数是" + e.data.length)
            //     }
            // });

            window.currentCustomId = e.data.id;


        },
        "export": function (e) {
            var token = layui.data('Edums')['x-auth-token'];
            window.open('/estimateCommits/export?x-auth-token=' + token + '&estimateId=' + e.data.id);
        }
    };
});