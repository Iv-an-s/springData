angular.module('market-front').controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }

    $scope.goToPay = function (orderId) {
            $location.path('/order_pay/' + orderId); //переходим по этому адресу
    }

    $scope.isStatusCorrect = function (status) {
            if (status == "ORDER_CREATED") {
                return true;
            } else {
                return false;
            }
        };

    $scope.loadOrders();
});

