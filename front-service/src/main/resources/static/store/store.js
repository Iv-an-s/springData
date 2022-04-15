angular.module('market-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    let currentPageIndex = 1;

    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                category: $scope.filter ? $scope.filter.category : null,
            }
        }).then(function (response) {
            console.log(response);
            $scope.ProductsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
        });
    };

    $scope.createNewProduct = function(){
        $http.post(contextPath + 'api/v1/products', $scope.new_product)
            .then(function successCallback (response){
                $scope.loadProducts(currentPageIndex);
                $scope.new_product = null;
            }, function failureCallback (response){
                alert(response.data.message);
            });
    };

    $scope.updateProduct = function(){
            console.log($scope.updated_product)
            $http.put(contextPath + 'api/v1/products', $scope.updated_product)
                .then(function successCallback (response){
                    console.log(response)
                    $scope.loadProducts(currentPageIndex);
                    $scope.updated_product = null;
                }, function failureCallback (response){
                    alert(response.data.message);
                });
        };

    $scope.prepareProductForUpdate = function(productId){
        $http.get(contextPath + 'api/v1/products/' + productId)
                        .then(function successCallback (response){
                            $scope.updated_product = response.data;
                        }, function failureCallback (response){
                            alert(response.data.message);
                        });
    }



    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springWebGuestCartId + '/add/' + productId)
            .then(function successCallback(response) {
            console.log(response);
            }, function errorCallback(response) {
            alert(response.data.message);
            });
    };

    $scope.generatePagesIndexes = function(startPage, endPage){
        let arr = [];
        for (let i = startPage; i < endPage +1; i++){
        arr.push(i);
        };
        return arr;
    };

    $scope.loadProducts();
});