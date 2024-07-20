function addPotluckItem(){
    const potluck_list = document.getElementById("potluck_list");
    const counter = potluck_list.children.length;
    //name
    const div = document.createElement("div");
    const label_item = document.createElement("label");
    label_item.setAttribute("for", "potluck"+counter)
    label_item.innerText = "Item " + counter + ": ";
    const input_item = document.createElement("input")
    input_item.type = "text";
    // input_item.id = "potluck"+counter;
    input_item.name = `potluckItems[${counter}].itemName`
    input_item.placeholder = "soda";
    //quantity
    const label_quantity = document.createElement("label");
    label_quantity.setAttribute("for", "quantity"+counter)
    label_quantity.innerText = "Quantity :";
    const input_quantity = document.createElement("input")
    input_quantity.type = "number";
    // input_quantity.id = "quantity"+counter;
    input_quantity.name = `potluckItems[${counter}].quantity`
    input_quantity.min = "0";
    div.appendChild(label_item);
    div.appendChild(input_item);
    div.appendChild(label_quantity);
    div.appendChild(input_quantity);
    potluck_list.appendChild(div);
}