@autocomplete = ->
  $('.autocomplete').each ->
    $(this).autocomplete
      source: $(this).data('source')
      minLength: 4
    $(this).autocomplete('instance')._renderItem = (ul, item) ->
      $('<li>').append(item.label).appendTo ul
      
$(document).on('turbolinks:load', @autocomplete);
