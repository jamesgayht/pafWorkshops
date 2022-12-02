function appendToUrl() {
    var action_src = "http://localhost:8080/order/total/" + document.getElementsByName("orderId")[0].value;
    var index_form = document.getElementById('index_form');
    index_form.action = action_src; 
}