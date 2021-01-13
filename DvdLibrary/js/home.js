$(document).ready(function () {
    loadDvds();
    addDvds();
    updateDvd();
    searchDvds();
});

function loadDvds() {
    clearDvdTable();
    var dvdRows = $('#dvdRows');

    $.ajax({
        type: 'GET',
        url: 'https://tsg-dvds.herokuapp.com/dvds',
        success: function(dvdArray) {
            $.each(dvdArray, function(index, dvd){
                var title = dvd.title;
                var releaseYear = dvd.releaseYear;
                var director = dvd.director;
                var rating = dvd.rating;
                var dvdId = dvd.id;
                var row = '<tr>';
                    row += '<td style="text-align:center"><button type="button" class="btn btn-link" onclick="displayDvd(' + dvdId + ')">' + title + '</button></td>';
                    row += '<td style="text-align:center">' + releaseYear + '</td>';
                    row += '<td style="text-align:center">' + director + '</td>';
                    row += '<td style="text-align:center">' + rating + '</td>';
                    row += '<td style="text-align:center"><button type="button" class="btn btn-link" onclick="showEditForm(' + dvdId + ')">Edit</button>';
                    row += '|<button type="button" class="btn btn-link" onclick="deleteDvd(' + dvdId + ')">Delete</button></td>';
                    row += '</tr>';
                dvdRows.append(row);
            })
        },
        error: function() {
            $('#loadErrorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    });
}

function addDvds() {
    $('#createDvd').click(function(event) {
        var haveValidationErrors = checkAndDisplayCreateValidationErrors($('#addForm').find('input'));
        if(haveValidationErrors) {
            return false;
        }
        $.ajax({
            type: 'POST',
            url: 'https://tsg-dvds.herokuapp.com/dvd',
            data: JSON.stringify({
                title: $('#addTitle').val(),
                releaseYear: $('#addYear').val(),
                director: $('#addDirector').val(),
                rating: $('#chooseRating').val(),
                notes: $('#addNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function() {

                $('#createErrorMessages').empty();
                $('#addTitle').val('');
                $('#addYear').val('');
                $('#addDirector').val('');
                $('#chooseRating').val('G');
                $('#addNotes').val('');
                loadDvds();
                $('#main').show();
                $('#create').hide();
            },
            error: function () {
                $('#createErrorMessages')
                    .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
            }
        })
    });
}

function clearDvdTable() {
    $('#dvdRows').empty();
}

function showEditForm(dvdId) {
    $('#loadErrorMessages').empty();
    $('#searchCategory').val('');
    $('#searchTerm').val('');
    $.ajax({
        type: 'GET',
        url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
        success: function(data, status) {
            $('#editTitle').val(data.title);
            $('#editYear').val(data.releaseYear);
            $('#editDirector').val(data.director);
            $('#editRating').val(data.rating);
            $('#editNotes').val(data.notes);
            $('#editDvdId').val(data.id);
            $('#editHeader').empty();
            $('#editHeader').append('Edit Dvd: ' + data.title);
            $('#main').hide();
            $('#edit').show();
        },
        error: function() {
            $('#loadErrorMessages')
            .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error calling web service. Please try again later.'));
        }
    })
}

function hideEditForm() {
    $('#editErrorMessages').empty();

    $('#editTitle').val('');
    $('#editYear').val('');
    $('#editDirector').val('');
    $('#editRating').val('G');
    $('#editNotes').val('');
    $('#main').show();
    $('#edit').hide();
}

function hideCreateForm() {
    $('#createErrorMessages').empty();

    $('#addTitle').val('');
    $('#addYear').val('');
    $('#addDirector').val('');
    $('#chooseRating').val('G');
    $('#addNotes').val('');
    $('#main').show();
    $('#create').hide();
}

function showCreateForm() {
    $('#loadErrorMessages').empty();
    $('#searchCategory').val('');
    $('#searchTerm').val('');
    $('#main').hide();
    $('#create').show();
}

function updateDvd(dvdId) {
    $('#saveChanges').click(function(event) {
        var haveValidationErrors = checkAndDisplayEditValidationErrors($('#editForm').find('input'));
        if(haveValidationErrors) {
            return false;
        }
        $.ajax({
            type: 'PUT',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + $('#editDvdId').val(),
            data: JSON.stringify({
                id: $('#editDvdId').val(),
                title: $('#editTitle').val(),
                releaseYear: $('#editYear').val(),
                director: $('#editDirector').val(),
                rating: $('#editRating').val(),
                notes: $('#editNotes').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function() {
                $('#editErrorMessages').empty();
                hideEditForm();
                loadDvds();
            },
            error: function(error) {
                $('#editErrorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
            }
        })
    })
}

function deleteDvd(dvdId) {
    if (confirm("Are you sure you want to delete this Dvd from your collection?")) {
        $.ajax({
            type: 'DELETE',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
            success: function() {
                loadDvds();
            }
        });
    }
}

function checkAndDisplayCreateValidationErrors(input) {
    $('#createErrorMessages').empty();

    errorMessages = checkAndDisplayValidationErrors(input);

    if (errorMessages.length > 0) {
        $.each(errorMessages,function(index,message) {
            $('#createErrorMessages').append($('<div>').attr({class: 'border border-dark rounded-0 list-group-item list-group-item-danger'}).text(message));
        });
        return true;
    } else {
        return false;
    }
}

function checkAndDisplayEditValidationErrors(input) {
    $('#editErrorMessages').empty();

    errorMessages = checkAndDisplayValidationErrors(input);

    if (errorMessages.length > 0) {
        $.each(errorMessages,function(index,message) {
            $('#editErrorMessages').append($('<div>').attr({class: 'border border-dark rounded-0 list-group-item list-group-item-danger'}).text(message));
        });
        return true;
    } else {
        return false;
    }
}

function checkAndDisplaySearchValidationErrors(input) {
    $('#loadErrorMessages').empty();

    errorMessages = checkAndDisplayValidationErrors(input);

    if (errorMessages.length > 0) {
        $('#loadErrorMessages')
            .append($('<div>')
            .attr({class: 'border border-dark rounded-0 list-group-item'})
            .css("background-color", "#ffcccb")
            .text("Both Search Category and Search Term are required"));
        return true;
    } else {
        return false;
    }
}

function checkAndDisplayValidationErrors(input) {

    var errorMessages = [];

    input.each(function() {
        if (!this.validity.valid) {
            if(this.id == "searchCategory" || this.id == "searchTerm") {
                errorMessages.push('');
            } else if(this.id == "editTitle" || this.id == "addTitle") {
                errorMessages.push('Please enter a title for the Dvd');

            } else if(this.id == "editYear" || this.id == "addYear") {
                errorMessages.push('Please enter a 4-digit year');
            }
        }
    });
    return errorMessages;
}

function searchDvds() {
    $('#search').click(function(event) {
        var haveValidationErrors = checkAndDisplaySearchValidationErrors($('.searchForm'));
        if(haveValidationErrors) {
            return false;
        }
        clearDvdTable();
        var dvdRows = $('#dvdRows');
        
        $.ajax({
            type: 'GET',
            url: 'https://tsg-dvds.herokuapp.com/dvds/' + $('#searchCategory').val() + '/' + $('#searchTerm').val(),
            success: function(dvdArray) {
                $.each(dvdArray, function(index, dvd){
                    var title = dvd.title;
                    var releaseYear = dvd.releaseYear;
                    var director = dvd.director;
                    var rating = dvd.rating;
                    var dvdId = dvd.id;
                    var row = '<tr>';
                        row += '<td style="text-align:center"><button type="button" class="btn btn-link">' + title + '</button></td>';
                        row += '<td style="text-align:center">' + releaseYear + '</td>';
                        row += '<td style="text-align:center">' + director + '</td>';
                        row += '<td style="text-align:center">' + rating + '</td>';
                        row += '<td style="text-align:center"><button type="button" class="btn btn-link" onclick="showEditForm(' + dvdId + ')">Edit</button>';
                        row += '|<button type="button" class="btn btn-link" onclick="deleteDvd(' + dvdId + ')">Delete</button></td>';
                        row += '</tr>';
                    dvdRows.append(row);
                })
            },
            error: function() {
                $('#loadErrorMessages')
                    .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
            }
        });
    })
}

function displayDvd(dvdId) {
    $('#loadErrorMessages').empty();
    $('#searchCategory').val('');
    $('#searchTerm').val('');
    $.ajax({
        type: 'GET',
        url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
        success: function(data, status) {
            $('#displayHeader').append(data.title);
            $('#displayYear').append(data.releaseYear);
            $('#displayDirector').append(data.director);
            $('#displayRating').append(data.rating);
            $('#displayNotes').append(data.notes);
            $('#main').hide();
            $('#id').show();
        },
        error: function() {
            $('#loadErrorMessages')
            .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error calling web service. Please try again later.'));
        }
    })
}

function hideIdForm() {
    $('#displayHeader').empty();
    $('#displayYear').empty();
    $('#displayDirector').empty();
    $('#displayRating').empty();
    $('#displayNotes').empty();
    $('#main').show();
    $('#id').hide();
}