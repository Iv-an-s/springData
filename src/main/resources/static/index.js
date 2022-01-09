angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage){
    const contextPath = 'http://localhost:8189/app/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

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

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
        });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get('http://localhost:8189/app/api/v1/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
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
    };

    $scope.loadCart = function (){
        $http.get(contextPath + '/carts')
        .then(function (response) {
            $scope.Cart = response.data;
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

    $scope.clearCart = function (){
        $http.get(contextPath + '/carts/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function (){
            $http({
                url: contextPath + '/orders',
                method: 'POST',
                params:{
                    address: $scope.order.Address,
                    phone: $scope.order.Phone
                }
            }).then(function successCallback(response) {
            $scope.loadCart();
        });
    }

    $scope.loadProducts();
    $scope.loadCart();
});