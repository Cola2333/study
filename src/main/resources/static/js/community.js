
/*
* 提交回复
* */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    if (!commentContent) {
        alert("回复不能为空~");
        return;
    }

    $.ajax({
       type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }),
        success: function (response) {
           if (response.code == 200) {
               window.location.reload();
           }
           else {
               if (response.code == 2003) {
                   var isAccept = confirm(response.message);
                   if (isAccept == true) {
                       window.open("https://github.com/login/oauth/authorize?client_id=367c20aae8ce7d906d23&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                       window.localStorage.setItem("closable", true);
                   }
               }
               else {
                   alert(response.message);
               }
           }
        },
        datatype: "json"
    });
}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment_" + id);
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        //标记二级评论状态
        e.removeAttribute("data-collapse");
        e.classList.remove("isIn");
    }else {
        //展开二级评论
        comments.addClass("in");
        //标记二级评论状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("isIn");
    }
}