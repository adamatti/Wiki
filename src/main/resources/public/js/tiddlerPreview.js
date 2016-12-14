$(function() {
  $( "#tabs" ).tabs();

  $("#editLink").on("click", function() {
    $('#tabs ul li').removeClass('active');
    $('#editLink').closest("li").addClass('active');
  });

  $("#previewLink").on("click", function() {
    var formData = {
            'name'              : $('input[name=name]').val(),
            'body'              : $('textarea[name=body]').val(),
            'type'              : $('select[name=type]').val()
        };

    $.ajax({
        type        : 'POST', // define the type of HTTP verb we want to use (POST for our form)
        url         : '/api/preview', // the url where we want to POST
        dataType    : 'json',
        data        : formData, // our data object
        encode          : true,
        success: function(data){
          $('#preview').html(data.html);
          $('#tabs ul li').removeClass('active');
          $('#previewLink').closest("li").addClass('active');
          console.log(data);
        },
        error: function(result) {
          alert("Error " + result);
        }
    });
  });
});
