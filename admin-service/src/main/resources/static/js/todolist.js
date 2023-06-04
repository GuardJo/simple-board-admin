$(document).ready(() => {
    $('#todo-button').click(() => {
        const text = $('#todo-input').val();
        const todoElement = `
            <li>
                <!-- drag handling -->
                <span class="handle ui-sortable-handle">
                    <i class="fas fa-ellipsis-v"></i>
                    <i class="fas fa-ellipsis-v"></i>
                </span>

                <!-- checkbox -->
                <div class="icheck-primary d-inline ml-2">
                    <input type="checkbox" value="" name="todo1">
                    <label for="todoCheck1"></label>
                </div>

                <span class="text">${text}</span>

                <!-- General search, delete or edit -->
                <div class="tools">
                    <i class="fas fa-edit"></i>
                    <i class="fas fa-trash-o"></i>
                </div>
            </li>
            `;

        $('ul.todo-list').append(todoElement);
    });

    $('#todo-input').keyup((event) => {
        if (event.keyCode === 13) {
            $('#todo-button').click();
        }
    });

    $(document).on('dbclick', 'ul.todo-list', function () {
        $(this).toggleClass('done').fadeOut('slow');
    });
});