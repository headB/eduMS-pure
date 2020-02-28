/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer","common","xmSelect","upload"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        xmSelect = layui.xmSelect,
        upload = layui.upload,
        common = layui.common,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentJob-manage",
        url: "/studentJobs",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "studentName", title: "学员"},
            {field: "subjectName", title: "学科"},
            {field: "classinfoName", title: "班级"},
            {field: "companyName", title: "公司名称"},
            {field: "companyCity", title: "公司城市"},
            {field: "companyBusiness", title: "公司行业",hide:true},
            {field: "companyScale", title: "公司规模",hide:true},
            {field: "companyNature", title: "公司性质"},
            {field: "workDate", title: "工作时间"},
            {field: "jobName", title: "就职岗位"},
            {field: "salary", title: "岗位薪资"},
            {field: "status", title: "状态", templet: function (row) {
                    return common.genYesOrNoTag(row.status < 0, '离职', '在职');
                }},
            {field: "resignationDate", title: "离职时间",hide:true},
            {field: "resignationReason", title: "离职原因",hide:true},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#studentJob-list-manage-btn"}
        ]],
        toolbar: '#studentJob-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-studentJob-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-studentJob-manage)", function (e) {
        active[e.event](e);
    }), e("studentJob", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-studentJob-manage')
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
                    url: '/studentJobs',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-studentJob-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['1100px', '500px'],
                id: 'LAY-popup-studentJob-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/studentJob/form').done(function () {
                        initForm(null,index);
                    });
                }
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['1100px', '500px'],
                id: "LAY-popup-studentJob-add",
                success: function (e, i) {
                    view(this.id).render("teaching/studentJob/form", data).done(function () {
                        initForm(data,i);
                    });
                }
            });
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/studentJobs',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        },
        "import":function (e) {
            admin.popup({
                title: "导入",
                area: ["500px", "450px"],
                id: "LAY-popup-studentJob-import",
                success: function (e, i) {
                    view(this.id).render("teaching/studentJob/import").done(function () {
                        var index;
                        //上传文件
                        upload.render({
                            elem: '#choose'
                            ,url: '/studentJobs?type=excel'
                            ,auto: false
                            ,accept: 'file'
                            ,acceptMime:'application/vnd.ms-excel'
                            ,bindAction: '#upload'
                            ,headers: {'x-auth-token': token}
                            ,before:function () {
                                index=layer.msg('数据导入中', {
                                    icon: 16,
                                    time : false,
                                    shade: 0.01
                                });
                            }
                            ,done: function (e) {
                                layer.close(index);
                                if (e.code === 0) {
                                    layer.msg(e.msg);
                                    setTimeout(function () {
                                        layui.table.reload("LAY-studentJob-manage"), layer.close(i)
                                    }, 1000);
                                }else{
                                    layer.msg(e.msg, {
                                        icon: 2,
                                        time : 5000,
                                        shade: 0.01
                                    });
                                }
                            }
                        });
                    })
                }
            });
        }
    };
    function initForm(data,i){
        $("#f_subjectId").html(subjects);
        $("#f_classinfoId").html(classinfos);
        var option = [];
        var type = "post";
        if(data){
            option = [{
                name:data.studentName,
                value:data.studentId,
                selected:true
            }];
            type = "put";
            // 数据回显
            form.val("layadmin-studentJob-formlist",data);
        }

        //学员下拉框
        xmSelect.render({
            el: '#f_studentId',
            filterable: true,
            remoteSearch: true,
            radio: true,
            clickClose: true,
            name: 'studentId',
            model: {
                label: {
                    type: 'text',
                    text: {
                        //左边拼接的字符
                        left: '',
                        //右边拼接的字符
                        right: '',
                        //中间的分隔符
                        separator: ', ',
                    },
                }
            },
            data:option,
            remoteMethod: function(val, cb, show){ //远程模糊查询
                //判断是否中文
                var pattern = new RegExp("[\u4E00-\u9FA5]+");
                if(pattern.test(val)){
                    admin.req({
                        url: '/students?size=-1&trueName=' + val,
                        done: function (res) {
                            var options = ''
                            if (res.data && res.data.length) {
                                var arr = [];
                                $.each(res.data, function (i, e) {
                                    arr[i] = {
                                        name:e.trueName,
                                        value : e.id
                                    }
                                });
                                cb(arr)
                            }
                        }
                    });
                }else{
                    cb([])
                }

            }
        })
        // 重新渲染页面
        form.render(null, "layadmin-studentJob-formlist");

        // 绑定提交表单事件
        form.on("submit(LAY-studentJob-front-submit)", function (e) {
            // 提交数据到后台
            admin.req({
                url: '/studentJobs',
                type: type,
                data: e.field,
                done: function (e) {
                    layer.msg(e.msg);
                    if (e.code === 0) {
                        setTimeout(function () {
                            layui.table.reload("LAY-studentJob-manage"), layer.close(i)
                        }, 1000);
                    }
                }
            });
            return false;
        });
    }
});