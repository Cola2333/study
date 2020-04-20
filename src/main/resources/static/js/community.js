function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();

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
               $("#comment_section").hide();
           }
           else {
               if (response.code == 2003) {
                   var isAccept = confirm(response.message);
                   if (isAccept == true) {
                       window.open("https://github.com/login/oauth/authorize?client_id=367c20aae8ce7d906d23&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                       window.localStorage.setItem("closable", true);
                   }
               }
           }
        },
        datatype: "json"
    });
}