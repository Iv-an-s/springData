angular.module('app', []).controller('indexController', function ($scope, $http){
    const contextPath = 'http://localhost:8189/app';

    $scope.loadProducts = function(){
        $http.get(contextPath + '/products')
            .then(function(response){
                $scope.ProductList=response.data;
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
    }

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
            .then(function (response) {
                //alert('DELETED');
                $scope.loadProducts();
            });
    }

    $scope.minMaxFilter = function (min, max) {
            $http({
                url: contextPath + '/products/price_between',
                method: 'get',
                params: {
                    min: min,
                    max: max
                }
            }).then(function (response) {
                $scope.loadProducts;
            });
        }


    $scope.loadProducts();
});