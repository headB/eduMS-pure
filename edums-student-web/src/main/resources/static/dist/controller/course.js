/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer","upload", "common"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        upload = layui.upload,
        common = layui.common,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
        var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        table.render({
        elem: "#LAY-course-manage",
        url: "/courses",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: 'numbers', title: '序号', width: 80},
            {field: "classinfoTitle", title: "所属班级",width: 280},
            {field: "courseName", title: "课程名称",width: 250},
            {field: "vdate", title: "日期",width: 130},
            {field: "week", title: "星期"},
            {field: "hasCourse", title: "是否上课",templet:function (d) {
                    return common.genYesOrNoTag(d.hasCourse < 0);
                }},
            {field: "hasTest", title: "是否测试",templet:function (d) {
                    return common.genYesOrNoTag(d.hasTest < 0);
                }},
            {field: "teacherName", title: "主讲老师"},
            {field: "counselorName", title: "辅导老师"},
            {field: "remake", title: "备注"},
            // {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#course-list-manage-btn"}
        ]],
        toolbar: '#course-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-course-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-course-manage)", function (e) {
        active[e.event](e);
    }), e("course", {});


    //事件
    var active = {
        "export": function (e) {
            layer.confirm('确定导出吗？', function (index) {
                var token = layui.data('Edums')['x-auth-token'];
                window.open('/courses?type=excel&x-auth-token='+token+'&classinfoId='+$("#classinfoId").val());
                layer.close(index);
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '450px'],
                id: 'LAY-popup-course-add',
                success: function (layero, index) {
                    view(this.id).render('system/course/form').done(function () {
                        form.render(null, 'layadmin-course-formlist');
                        //监听提交
                        form.on('submit(LAY-course-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/courses',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-course-manage"), layer.close(index)
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
                id: "LAY-popup-course-add",
                success: function (e, i) {
                    view(this.id).render("system/course/form", data).done(function () {
                        // 数据回显
                        form.val("layadmin-course-formlist", data);
                        // 禁用用户名表单
                        $("input[name=username]").prop("disabled", true).prop("readOnly", true);
                        // 重新渲染页面
                        form.render(null, "layadmin-course-formlist"),
                        // 绑定提交表单事件
                        form.on("submit(LAY-course-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/courses',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-course-manage"), layer.close(i)
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
                        url: '/courses',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        },"import":function (e) {
            admin.popup({
                title: "导入",
                area: ["500px", "450px"],
                id: "LAY-popup-course-import",
                success: function (e, i) {
                    view(this.id).render("teaching/course/import").done(function () {
                        var index ;
                        //上传文件
                        upload.render({
                            elem: '#choose'
                            ,url: '/courses?type=excel'
                            ,auto: false
                            ,accept: 'file'
                            ,acceptMime:'application/vnd.ms-excel'
                            ,bindAction: '#upload'
                            ,headers: {'x-auth-token': token}
                            ,data: {
                                classinfoId: function(){
                                    return $('#t_classinfoId').val();
                                }
                            }
                            ,before:function () {
                                index=layer.msg('数据导入中', {
                                    icon: 16,
                                    time : false,
                                    shade: 0.01
                                });
                            }
                            ,done: function (e) {
                                layer.msg(e.msg);
                                if (e.code === 0) {
                                    layer.close(index);
                                    setTimeout(function () {
                                        layui.table.reload("LAY-course-manage"), layer.close(i)
                                    }, 1000);
                                }
                            }
                        });
                    })
                }
            });
        }
    };
});