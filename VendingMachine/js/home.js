//Main function
$(document).ready(function() {
    //initialize placeholder money to 0
    var placeholder = 0.00;
    //load all of the vending machine items
    loadItems();
    //change placeholder money depending on button clicks
    placeholder = changeMoney(placeholder);
});

//loads the vending machine items
function loadItems() {
    //set variable equal to div where items will be stored
    var items = $('#items');

    //ajax call to get vending machine items
    $.ajax({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function(itemArray) {
            //initalize html row to append items
            var row = '';
            //append each item to html 
            $.each(itemArray, function(index, item) {
                //increase the index because our index starts at 1
                var itemIndex = index + 1;
                //initialize variables returned from ajax call
                var itemId = item.id;
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                //insert row after every three items
                if (index % 3 == 0) {
                    row += '<div class="row">';
                }
                    //insert all html 
                    row += '<div class="col-md-4 item">';
                    row += '<button type="button" id="' + itemId + '" class="btn offset-md-1 col-md-10 border border-dark rounded-0" onclick="select(' + itemId + ')">';
                    row += '<p class="index">' + itemIndex + '</p>';
                    row += '<p class="name">' + name + '</p>';
                    row += '<p class="price">$' + (price.toFixed(2)) + '</p>';
                    row += '<p class="quantity">Quantity Left: ' + quantity + '</p>';
                    row += '</button>';
                    row += '</div>';
                //end row after every three items
                if (index % 3 == 2) {
                    row += '</div><br>';
                }
            })
            //put the html into the document
            items.append(row);
        },
        error: function() {
            //alert message if data not found
            alert("Error accessing data");
        }
    });
}

//function for all of the button clicks
function changeMoney(placeholder) {
    //initialize change to 0
    var change = -1;
    //function that adds a dollar
    $('#addDollar').click(function(event) {
        placeholder += 1.00;
        $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
        return (placeholder);
    })
    //function that adds a quarter
    $('#addQuarter').click(function(event) {
        placeholder += 0.25;
        $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
        return (placeholder);
    })
    //function that adds a dime
    $('#addDime').click(function(event) {
        placeholder += 0.10;
        $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
        return (placeholder);
    })
    //function that adds a nickel
    $('#addNickel').click(function(event) {
        placeholder += 0.05;
        $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
        return (placeholder);
    })
    //purchases item
    $('#makePurchase').click(function(event) {
        change = purchase();
        //if no errors occured set placeholder equl to change
        if (change > -1) {
            placeholder = change;
        }
        return (placeholder);
    })
    //returns change
    $('#changeReturn').click(function(event) {
        //run function if there is change to return
        if (change > 0) {
            //return change and update placeholder
            placeholder = exactChange(placeholder, change);
            //reset change to 0
            change = 0;
        }
        return (placeholder);
    })
}

//select item to purchase
function select(itemId) {
    //gets the index of the current item;
    var itemIndex = $('#' + itemId + ' .index').text();
    //puts index in the item display
    $('#item').attr('placeholder', itemIndex);
    //sets the hidden value to the id of the item
    $('#itemId').val(itemId);
}

//purchase selected item
function purchase() {
    //if there is no item selected output error
    if(!($('#itemId').val())) {
        $('#messages').attr('placeholder', 'Please make a selection');
    }else {
        //set return value to error
        var placeholder = -1;
        //ajax call to purchase item
        $.ajax({
            async:false,
            type: 'POST',
            url: 'http://tsg-vending.herokuapp.com/money/' + $('#moneyDisplay').attr('placeholder') + '/item/' + $('#itemId').val(),
            'dataType': 'json',
            success: function(change) {
                //empty and reload the items
                $('#items').empty();
                loadItems();
                //update success message
                $('#messages').attr('placeholder', 'Thank You!!!');
                //reset selected item
                $('#itemId').val('');
                $('#item').attr('placeholder', '');
                //initialize variables returned from ajax call
                var quarters = change.quarters;
                var dimes = change.dimes;
                var nickels = change.nickels;
                var pennies = change.pennies;
                //set return value equal to total change
                placeholder = ((0.25) * quarters) + ((0.10) * dimes) + ((0.05) * nickels) + ((0.01) * pennies);
                //initialize change message
                var text = "";
                //boolean that tests if there are multiple types of coins used
                var multiple = false;
                //if there are quarters
                if (quarters > 0) {
                    //add quarters to change message
                    text += quarters + " quarter";
                    //if there are more than one quarter pluralize
                    if (quarters > 1) {
                        text += "s";
                    }
                    //set multiple types of coins to true
                    multiple = true;
                }
                //if there are dimes
                if (dimes > 0) {
                    //if there are multiple types of coins add a leading comma
                    if (multiple) {
                        text += ", ";
                    }
                    //add dimes to change message
                    text += dimes + " dime";
                    //if there are more than one dime pluralize
                    if (dimes > 1) {
                        text += "s";
                    }
                    //set multiple types of coins to true
                    multiple = true;
                }
                //if there are nickels
                if (nickels > 0) {
                    //if there are multiple types of coins add a leading comma
                    if (multiple) {
                        text += ", ";
                    }
                    //add nickels to change message
                    text += nickels + " nickel";
                    //if there are more than one nickel pluralize
                    if (nickels > 1) {
                        text += "s";
                    }
                    //set multiple types of coins to true
                    multiple = true;
                }
                //if there are pennies
                if (pennies > 0) {
                    //if there are multiple types of coins add a leading comma
                    if (multiple) {
                        text += ", ";
                    }
                    //add pennies to change message
                    text += pennies;
                    //if there is a single penny add to change message
                    if (pennies == 1) {
                        text += " penny";
                    //if there is more than one penny pluralize
                    } else {
                        text += " pennies";
                    }
                }
                //display change
                $('#change').attr('placeholder', text);
                //display change in money display
                $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
            },
            error: function(item) {
                //stores error message in a variable
                var err = eval("(" + item.responseText + ")");
                //display error message
                $('#messages').attr('placeholder', err.message);
                //empty and reload the items
                $('#items').empty();
                loadItems();
            }
        })
        //return the updated money
        return placeholder;
    }
}

//returns exact change
function exactChange(placeholder, change) {
    //subtract change from current money
    placeholder -= change;
    //display updated money
    $('#moneyDisplay').attr('placeholder', placeholder.toFixed(2));
    //clear all display windows
    $('#change').attr('placeholder', '');
    $('#messages').attr('placeholder', '');
    $('#item').attr('placeholder', '');
    //empty and reload the items
    $('#items').empty();
    loadItems();
    //return current value of money
    return(placeholder);
}