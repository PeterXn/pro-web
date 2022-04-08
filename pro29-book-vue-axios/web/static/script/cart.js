function editCart(cartItemId, buyCount) {
    window.location.href = 'cart.do?operate=editCart&cartItemId=' + cartItemId + '&buyCount=' + buyCount;
}

window.onload = function () {
    var vue = new Vue({
        el: "#cart_div",
        data: {
            cart:{}
        },
        methods: {
            getCart: function () {
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'cartInfo',
                    }
                })
                    .then(function (value) {
                        console.log(value.data);
                        vue.cart = value.data;
                    })
                    .catch(function (reason) {
                        console.error(reason.data);
                    });
            },
            editCartAxios:function (cartItemId, buyCount){
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'editCartAxios',
                        cartItemId: cartItemId,
                        buyCount: buyCount
                    }
                })
                    .then(function (value) {
                        vue.getCart();
                    })
                    .catch(function (reason) {
                        console.error(reason.data);
                    });
            },
            delCartAxios: function (cartItemId){
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'delCartAxios',
                        cartItemId: cartItemId,
                    }
                })
                    .then(function (value) {
                        vue.getCart();
                    })
                    .catch(function (reason) {
                        console.error(reason.data);
                    });
            }
        },
        mounted: function () {
            this.getCart();
        }
    });
}