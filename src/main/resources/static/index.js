angular.module('app', []).controller('indexController', function ($scope, $http){
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadProducts = function (pageIndex = 1) {
        console.log('Click!')
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                title_part: $scope.filter ? $scope.filter.title_part : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };


    $scope.changePrice = function (productId, delta) {
        console.log('Click!')
        $http({
            url: contextPath + '/products/change_price',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function(response){
            $scope.loadProducts();
        });
    };

    $scope.loadCart = function (){
        console.log('сработала функция loadCart')
        $http.get(contextPath + '/carts')
        .then(function (response) {
            $scope.Cart = response.data;
            console.log($scope.Cart)
        });
    };

    $scope.deleteProductFromCart = function (productId){
        $http({
            url: contextPath + '/carts/remove_product/' + productId,
            method: 'GET',
            params: {
                productId: productId
            }
        }).then(function(response){
            $scope.loadCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/carts/add/' + productId,
            method: 'GET',
            params: {
                productId: productId
            }
            }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clear = function (){
        $http.get(contextPath + '/carts/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }


//    $scope.deleteProduct = function (productId) {
//        $http.delete(contextPath + '/products/delete/' + productId)
//            .then(function (response) {
//                //alert('DELETED');
//                $scope.loadProducts();
//            });
//    }

//    $scope.createProductJson = function (){
//        console.log($scope.newProductJson);
//        $http.post(contextPath + '/products', $scope.newProductJson)
//        .then(function (response){
//            $scope.loadProducts();
//            $scope.newProductJson = null;
//        })
//    }

//    $scope.sumTwoNumbers = function (){
//            console.log($scope.calcAdd);
//            $http({
//                url: contextPath + '/calc/add',
//                method: 'get',
//                params: {
//                    a: $scope.calcAdd.a,
//                    b: $scope.calcAdd.b
//                }
//            }).then(function(response){
//            alert('Сумма равна ' + response.data.value)
//            $scope.calcAdd = null;
//        });
//    }


    $scope.loadProducts();
    $scope.loadCart();
});