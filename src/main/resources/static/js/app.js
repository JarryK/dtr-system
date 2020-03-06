document.write("<script src='/js/SweetAlert2.js'></script>");
document.write("<script src='/js/template.js'></script>");
var App = function () {
    var globalSetting = function () {
        // 扩展时间日期格式功能
        Date.prototype.format = function (fmt) {
            fmt = (fmt = $.trim(fmt)) === '' ? 'yyyy/MM/dd hh:mm:ss' : fmt;
            var o = {
                "M+": this.getMonth() + 1,                 //月份
                "d+": this.getDate(),                    //日
                "h+": this.getHours(),                   //小时
                "m+": this.getMinutes(),                 //分
                "s+": this.getSeconds(),                 //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        };
        // 异步ajax报错异常处理
        // $(document).ajaxError(function(event, xhr, settings, thrownError) {
        //     App.topAlert(thrownError?thrownError:xhr.statusText,2);
        // });
        // 异步ajax全局参数设定
        $.ajaxSetup({timeout: '120000'});
        $.extend({
            post: function (url, data, options) {
                var _url = (((typeof url) == 'string') ? url : undefined);
                var _data = (((typeof data) == 'object' && data != null) ? JSON.stringify(data) : (((typeof data) == 'string') ? data : undefined));
                var _options = (((typeof options) == 'object' && options != null) ? $.extend(true, {}, options) : {});
                var _success = ($.isFunction(options) ? options : undefined);
                var _options_default = {
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    url: _url,
                    data: _data,
                    success: _success
                };
                var _options_apply = $.extend(true, {}, _options_default, _options);
                return $.ajax(_options_apply);
            },
        });
    };
    //page_turn页面跳转函数集
    var page_turn_functions = (function () {
        return {
            //在自身窗口打开
            goLoginBySelf: function () {
                window.open("/dtr/login", "_self");
            },
            goHomeBySelf: function () {
                window.open("/dtr/home", "_self", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes");
            },
            goIssueBySelf: function () {
                App.getUserMsg(function (uNbr, uName, uType) {
                    if (App.isTeacher(uType)) {
                        window.open('/dtr/issue', "_self");
                    } else {
                        App.alert('错误', '没有权限', 2);
                    }
                });
            },
            goReservationBySelf: function () {
                window.open('/dtr/reservation', "_self");
            },
            goEvaluateBySelf: function () {
                window.open('/dtr/evaluate', "_self");
            },
            goHistoryBySelf: function () {
                window.open('/dtr/history', "_self");
            },
            //新窗口打开
            goHomeByNewPage: function () {
                window.open('/dtr/home', "_blank");
            },
            goIssueByNewPage: function () {
                App.getUserMsg(function (uNbr, uName, uType) {
                    if (App.isTeacher(uType)) {
                        window.open('/dtr/issue', "_blank");
                    } else {
                        App.alert('错误', '没有权限', 2);
                    }
                });
            },
            goReservationByNewPage: function () {
                window.open('/dtr/reservation', "_blank");
            },
            goEvaluateByNewPage: function () {
                window.open('/dtr/evaluate', "_blank");
            },
            goHistoryByNewPage: function () {
                window.open('/dtr/history', "_blank");
            }
        }
    })();
    // utils工具函数集
    var utils_functions = (function () {
        return {
            loginOut: function (uNbr, uName) {
                App.selectAlert('操作提示', "用户" + uName + "确定退出吗?", 3, function () {
                    $.post('/dtr/user/loginOut', {
                        uNbr: uNbr
                    }).done(function (data) {
                        if (!App.checker(data)) {
                            return;
                        } else {
                            App.topAlert("退出成功！", 1, 1300, function () {
                                window.open("/dtr/login", "_self");
                            });
                            return;
                        }
                    });
                })
            },
            getUserMsg: function (callback) {
                $.post('/dtr/user/getUser').done(function (data) {
                    if (!App.checker(data)) {
                        App.alert('访问失败', '请先登录', 2, function () {
                            App.goLoginBySelf();
                        });
                    } else {
                        if ($.isFunction(callback)) {
                            callback(data.user.USER_NBR, data.user.USER_NAME, data.user.TYPE_NAME);
                            return;
                        }
                    }
                });
            },
            formatDateString: function (value) {
                return new Date(value).format('yyyy-MM-dd hh:mm:ss');
            },
        }
    })();
    // utils常用ui函数集
    var utils_ui_functions = (function () {
            return {
                alertClose: function () {
                    swal.close();
                },
                checkAlertType: function (type) {
                    switch (type) {
                        case 1:
                            return 'success';
                        case 2:
                            return 'error';
                        case 3:
                            return 'warning';
                        case 4:
                            return 'info';
                        case 5:
                            return 'question';
                        default:
                            return;
                    }
                },
                alert: function (title = '提示', text = '操作成功' , type = 1, callback) {
                    var wait = function (dtd) {
                        var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象
                        // var _title = (title == '' ? '成功' : title);
                        // var _type = (type == '' ? 1 : type);
                        // var _text = (text == '' ? '操作成功' : text);
                        var icon = App.checkAlertType(type);
                        Swal.fire({
                            title: title,
                            text: text,
                            icon: icon,
                            confirmButtonText: '确定',
                            allowEscapeKey: true,
                            backdrop: `rgba(0, 0, 0, 0.6)`
                        }).then(function () {
                            dtd.resolve();
                        });
                        return dtd.promise(); // 返回promise对象
                    };
                    $.when(wait()).done(function () {
                        if ($.isFunction(callback)) {
                            callback();
                            return;
                        }
                    });
                    return;
                },
                selectAlert: function (title = '操作提示', text = '确定吗', type = 1, callback) {
                    // var _title = (title === '' ? '操作提示' : title);
                    // var _type = (type === '' ? 1 : type);
                    // var _text = (text === '' ? '确定吗' : text);
                    var icon = App.checkAlertType(type);
                    Swal.fire({
                        icon: icon, // 弹框类型
                        title: title, //标题
                        text: text, //显示内容
                        confirmButtonColor: '#3085d6', // 确定按钮的 颜色
                        confirmButtonText: '确定', // 确定按钮的 文字
                        showCancelButton: true, // 是否显示取消按钮
                        cancelButtonColor: '#d33', // 取消按钮的 颜色
                        cancelButtonText: "取消", // 取消按钮的 文字
                        focusCancel: true, // 是否聚焦 取消按钮
                        reverseButtons: false // 是否 反转 两个按钮的位置 默认是  左边 确定  右边 取消
                    }).then(function (isConfirm) {
                        try {
                            //判断 是否 点击的 确定按钮
                            if (isConfirm.value) {
                                if ($.isFunction(callback)) {
                                    callback();
                                    return;
                                }
                                Swal.fire("成功", "点击了确定", "success");
                            } else {
                                return;
                            }
                        } catch (e) {
                            alert(e);
                        }
                    });
                },
                topAlert: function (title = '操作提示', type = 1, timer = 3000, callback) {
                    // var _title = (title === '' ? '操作提示' : title);
                    // var _type = (type === '' ? 1 : type);
                    // var _timer = (timer === '' ? 3000 : timer);
                    var wait = function (dtd) {
                        var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象
                        var icon = App.checkAlertType(type);
                        Swal.fire({
                            toast: true,
                            position: 'top',
                            showConfirmButton: false,
                            //时间进度条
                            // timerProgressBar:true,
                            timer: timer, //毫秒
                            icon: icon,
                            title: title
                        }).then(function () {
                            dtd.resolve();
                        });
                        return dtd.promise();
                    };
                    $.when(wait()).done(function () {
                        if ($.isFunction(callback)) {
                            callback();
                            return;
                        }
                    });
                    return;
                },
                msgAlert: function (content) {
                    Swal.fire({
                        title: '<strong>记录</strong>',
                        type: 'info',
                        html: content, // HTML
                        focusConfirm: true, //聚焦到确定按钮
                        showCloseButton: true,//右上角关闭
                    })

                }

                ,
                // inputAlert: function () {
                //     Swal.mixin({
                //         input: 'text',
                //         confirmButtonText: 'Next &rarr;',
                //         showCancelButton: true,
                //         progressSteps: ['1', '2', '3']
                //     }).queue([{
                //         title: 'Question 1',
                //         text: 'Chaining swal2 modals is easy'
                //     }, {
                //         title: 'Question 1',
                //         text: 'Chaining swal2 modals is easy'
                //
                //     }, {
                //         title: 'Question 1',
                //         text: 'Chaining swal2 modals is easy'
                //     }]).then(function(result){
                //         if (result.value) {
                //             const answers = JSON.stringify(result.value);
                //             Swal.fire({
                //                 title: 'All done!',
                //                 html: `Your answers:<pre><code>${answers}</code></pre>`,
                //                 confirmButtonText: 'Lovely!'
                //             })
                //         }
                //     })
                // },
                // 检验后端返回的数据成功or失败，并可以控制在屏幕顶部提示

                // 设置时间输入框
                setInputBoxForTime: function (_id_or_class) {
                    $(_id_or_class).datetimepicker({
                        bootcssVer: 4,
                        format: 'yyyy-mm-dd hh:ii',
                        todayBtn: 'linked',
                        todayHighlight: true,
                        autoClose: true,
                        Integer: 1,
                        startDate: new Date(),
                        // endDate
                        language: 'zh-CN'
                    });
                }
                ,
            }
        }
    )();
    // utils检验函数集
    var utils_checker = (function () {
        return {
            checker: function (_response, _tips) {
                _tips = _tips === undefined ? true : _tips;
                if (typeof (_response) !== 'object' || _response == null
                    || typeof (_response['health']) !== 'object' || _response['health'] == null) {
                    if (_tips) {
                        App.topTips('arguments error!');
                    }
                    return false;
                }
                var health = _response['health'];
                var _function_tips = function () {
                    if (_tips) {
                        App.topAlert(health.message, 2);
                    }
                };
                switch (+health.code) {
                    case 0:
                        return true;
                    default:
                        _function_tips();
                        return false;
                }
            },
            isTeacher: function (type) {
                return type === '教师';
            }
        }
    })();
    return {
        init: function () {
            // 初始化全局设置
            globalSetting($.extend(true, App, utils_functions, utils_ui_functions, utils_checker, page_turn_functions)); // 全局功能设置
            // 绑定全局事件
            // bindEvents();
            return this;
        },
    }
}();
$(function () {
    App.init();
});