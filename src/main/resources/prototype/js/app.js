document.write("<script src='/js/SweetAlert2.js'></script>");





var App = function () {
    var bindEvents = function () {
        $.fn.extend({
    
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
        alert: function (title = '成功', text, type = 1, callback) {
            var icon = this.checkAlertType(type);
            Swal.fire({
                title: title,
                text: text,
                icon: icon,
                confirmButtonText: '确定',
                allowEscapeKey: true,
                backdrop:`rgba(0,0,0,0.6)`
            }).then((isConfirm) => {
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
                reverseButtons: true // 是否 反转 两个按钮的位置 默认是  左边 确定  右边 取消
            }).then((isConfirm) => {
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
        
        topAlert: function (title,type = 1, timer = 3000) {
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
        },
        msgAlert:function(){
            Swal.fire({
                title: '<strong>记录</strong>',
                type: 'info',
                html: content, // HTML
                focusConfirm: true, //聚焦到确定按钮
                showCloseButton: true,//右上角关闭
            })
         
        },
        inputAlert: function () {
            Swal.mixin({
                input: 'text',
                confirmButtonText: 'Next &rarr;',
                showCancelButton: true,
                progressSteps: ['1', '2', '3']
            }).queue([{
                title: 'Question 1',
                text: 'Chaining swal2 modals is easy'
            }, {
                title: 'Question 1',
                text: 'Chaining swal2 modals is easy'

            }, {
                title: 'Question 1',
                text: 'Chaining swal2 modals is easy'
            }]).then((result) => {
                if (result.value) {
                    const answers = JSON.stringify(result.value)
                    Swal.fire({
                        title: 'All done!',
                        html: `
                      Your answers:
                      <pre><code>${answers}</code></pre>
                    `,
                        confirmButtonText: 'Lovely!'
                    })
                }
            })
        },
      
    }
}();
$(function () {
    App.init();
});