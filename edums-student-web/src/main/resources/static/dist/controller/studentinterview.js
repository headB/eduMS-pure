/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer","xmSelect","upload"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        upload = layui.upload,
        xmSelect = layui.xmSelect,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentInterview-manage",
        url: "/studentInterviews",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "studentName", title: "学员"},
            {field: "subjectName", title: "学科"},
            {field: "classinfoName", title: "班级"},
            {field: "companyName", title: "面试公司"},
            {field: "jobName", title: "面试岗位"},
            {field: "salary", title: "岗位薪资"},
            {field: "companyCity", title: "公司城市"},
            {field: "interviewTime", title: "面试时间"},
            {field: "interviewAddr", title: "面试地址",hide:true},
            {field: "interviewSituation", title: "面试情况"},
            {field: "studentInterviewComment", title: "公司评价",hide:true},
            {field: "teacherCompanyTag", title: "公司标签",hide:true},
            {field: "interviewQuestionUrl", title: "面试题", templet: function (row) {
                    if (row.interviewQuestionUrl){
                        return "<a style=\"color: green;text-decoration:underline; cursor:pointer\" href='"+row.interviewQuestionUrl+"'>下载</a>";
                    }
                    return "暂无";
                }},
            {field: "interviewRecordUrl", title: "面试录音", templet: function (row) {
                    if (row.interviewRecordUrl) {
                        return "<a style=\"color: green;text-decoration:underline; cursor:pointer\" href='" + row.interviewRecordUrl + "'>下载</a>";
                    }
                    return "暂无";
                }},
            {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#studentInterview-list-manage-btn"}
        ]],
        toolbar: '#studentInterview-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-studentInterview-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-studentInterview-manage)", function (e) {
        active[e.event](e);
    }), e("studentInterview", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-studentInterview-manage')
            , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定删除吗？', function (index) {
                var ids = $.map(checkData, function (v, i) {
                    return v.id
                });
                var stuIds = $.map(checkData, function (v, i) {
                    return v.studentId
                });
                //执行 Ajax 后重载
                admin.req({
                    url: '/studentInterviews',
                    type: 'delete',
                    data: {ids: ids,stuIds:stuIds},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-studentInterview-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['1100px', '650px'],
                id: 'LAY-popup-studentInterview-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/studentInterview/form').done(function () {
                        initForm(null,index);
                    });
                }
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['1100px', '550px'],
                id: "LAY-popup-studentInterview-add",
                success: function (e, i) {
                    view(this.id).render("teaching/studentInterview/form", data).done(function () {
                        initForm(data,i);
                    });
                }
            });
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/studentInterviews',
                        data: {ids: [e.data.id],stuIds:[e.data.studentId]},
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
                id: "LAY-popup-studentInterview-import",
                success: function (e, i) {
                    view(this.id).render("teaching/studentInterview/import").done(function () {
                        var index;
                        //上传文件
                        upload.render({
                            elem: '#choose'
                            ,url: '/studentInterviews?type=excel'
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
                                        layui.table.reload("LAY-studentInterview-manage"), layer.close(i)
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

            if (data.interviewQuestionUrl) {
                $("#downloadQuestion").show();
                $("#noquestionMsg").hide();
                $("#downloadQuestion").attr('href', data.interviewQuestionUrl);
            }
            if (data.interviewRecordUrl) {
                $("#downloadRecord").show();
                $("#norecordMsg").hide();
                $("#downloadRecord").attr('href', data.interviewRecordUrl);
            }

            type = "put";
            // 数据回显
            form.val("layadmin-studentInterview-formlist",data);
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
        form.render(null, "layadmin-studentInterview-formlist");

        // 绑定提交表单事件
        form.on("submit(LAY-studentInterview-front-submit)", function (e) {
            var dataField = new FormData($("#editForm")[0]);
            /**
             *  使用ajax提交form表单的上传文件，
             *  首先要使用FormData的获取表单的上次文件数据，
             */
            $.ajax({
                url: '/studentInterviews',
                type: type,
                contentType: false,
                processData: false,
                cache: false,
                headers: {'x-auth-token': token},
                data:  dataField,
                success: function (e) {
                    layer.msg(e.msg);
                    if (e.code === 0) {
                        if (e.data.interviewQuestionUrl) {
                            $("#downloadQuestion").show();
                            $("#noquestionMsg").hide();
                            $("#downloadQuestion").attr('href', e.data.interviewQuestionUrl);
                        }
                        if (e.data.interviewRecordUrl) {
                            $("#downloadRecord").show();
                            $("#norecordMsg").hide();
                            $("#downloadRecord").attr('href', e.data.interviewRecordUrl);
                        }
                        setTimeout(function () {
                            layui.table.reload("LAY-studentInterview-manage"), layer.close(i)
                        }, 1000);
                    }
                }

            });
            return false;
        });
    }
});