document.write("<script src='/js/SweetAlert2.js'></script>");
var App = function () {
    var bindEvents = function () {
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
    return {
        init: function () {
            // 初始化全局设置
            // globalSet();
            // 绑定全局事件
            bindEvents();
            return this;
        },
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
        setDefault: function (item, val) {
            return this.item = item || val;
        },
        alert: function (title = '成功', type = 1, text, callback) {
            App.setDefault(title, "成功");
            var icon = this.checkAlertType(type);
            Swal.fire({
                title: title,
                text: text,
                icon: icon,
                confirmButtonText: '确定',
                allowEscapeKey: true,
                backdrop: `rgba(0,0,0,0.6)`
            }).then(function (isConfirm) {
                try {
                    //判断 是否 点击的 确定按钮
                    if (isConfirm.value) {
                        if ($.isFunction(callback)) {
                            callback();
                            return;
                        }
                    }
                } catch (e) {
                    alert(e);
                }
            });
            return;
        },
        selectAlert: function (title = '操作提示', text = '确定吗？', type = 3, callback) {
            var icon = this.checkAlertType(type);
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

                    }
                } catch (e) {
                    alert(e);
                }
            });
        },
        topAlert: function (title = '操作提示', type = 1, timer = 3000) {
            var icon = this.checkAlertType(type);
            Swal.fire({
                toast: true,
                position: 'top',
                showConfirmButton: false,
                //时间进度条
                // timerProgressBar:true,
                timer: timer, //毫秒
                icon: icon,
                title: title
            })
        }
        ,
        msgAlert: function () {
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
        loginOut:function (uName) {
            App.selectAlert("用户<span style='color: red'>"+uName+"</span>确定退出吗?",'',5,function () {
                $.post('/dtr/user/loginOut',{
                    userName:uName
                }).done(function (data) {
                    if (!App.checker(data)){
                        return;
                    }else {
                        App.topAlert("退出成功！");
                            window.open("/dtr/login","_self");
                        return;
                    }
                });
            })
        },
    }
}();
$(function () {
    App.init();
});