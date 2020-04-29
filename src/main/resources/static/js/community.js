
/*
* 提交问题回复
* */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId, 1, commentContent);

}

/*
* 提交评论回复
* */
function reply(e) {
    var commentId = e.getAttribute("data-id");
    var replyContent = $("#reply_content_" + commentId).val();
    comment2target(commentId, 2, replyContent);
}

/*
* 对公用函数进行封装
* */
function comment2target(targetId, type, commentContent) {
    if (!commentContent) {
        alert("回复不能为空~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": commentContent,
            "type": type
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
    var collapse = e.getAttribute("data-collapse"); // 一开始是没有这个属性的 点击之后才有这个属性 这个属性相当于标记状态
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        //标记二级评论状态
        e.removeAttribute("data-collapse");
        e.classList.remove("isIn");
    }else {
        var subCommentContainer = $("#comment_" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }
        else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("isIn");
            });
        }
    }
}

function showSelectTag() {
  $("#select_tag").show();

}

function selectTag(e) {
    var tag = e.getAttribute("data-tag")
    var previous = $("#tag").val();
    if (previous.indexOf(tag) == - 1) {
        if (previous) {
            $("#tag").val(previous + ',' + tag);
        } else {
            $("#tag").val(tag);
        }
    }
    
}