angular.module('app', []).controller('indexController', function ($scope, $http){
    const contextPath = 'http://localhost:8189/app';

    $scope.loadProducts = function(){
        $http.get(contextPath + '/products')
            .then(function(response){
                $scope.ProductList=response.data;
        });
    }

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

    $scope.minMaxFilter = function () {
        console.log($scope.sortedList);
        $http({
            url: contextPath + '/products/price_between',
            method: 'GET',
            params: {
                min: $scope.sortedList.min,
                max: $scope.sortedList.max
            }
        }).then(function (response) {
            $scope.ProductList = response.data;
            $scope.sortedList.min = 0;
            $scope.sortedList.max = 1000000;
        });
    }

    $scope.createProductJson = function (){
        console.log($scope.newProductJson);
        $http.post(contextPath + '/products', $scope.newProductJson)
        .then(function (response){
            $scope.loadProducts();
            $scope.newProductJson = null;
        })
    }

    $scope.sumTwoNumbers = function (){
            console.log($scope.calcAdd);
            $http({
                url: contextPath + '/calc/add',
                method: 'get',
                params: {
                    a: $scope.calcAdd.a,
                    b: $scope.calcAdd.b
                }
            }).then(function(response){
            alert('Сумма равна ' + response.data.value)
            $scope.calcAdd = null;
        });
    }


    $scope.loadProducts();
});