angular.module('market-front').controller('analyticsController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/analytics/';

//    $scope.loadTopMonthProducts = function () {
//        console.log("topMonthProducts запрошен");
//        setTimeout(function(){
//        $http.get(contextPath + 'api/v1/topMonthProducts')
//            .then(function (response) {
//                $scope.TopMonthProducts = response.data;
//            });
//            }, 3000);
//    };
//
//    $scope.loadTopDayProducts = function () {
//        console.log("topDayProducts запрошен");
//        setTimeout(function(){
//        $http.get(contextPath + 'api/v1/topDayProducts')
//            .then(function (response) {
//                $scope.TopDayProducts = response.data;
//            });
//         }, 3000);
//    };

    $scope.loadTopMonthProducts = function () {
        console.log("topMonthProducts запрошен");
        $http.get(contextPath + 'api/v1/productsAnalytics/topOfMonth')
            .then(function (response) {
                console.log(response);
                $scope.TopMonthProducts = response.data;
            });
    };

    $scope.loadTopDayProducts = function () {
        console.log("topDayProducts запрошен");
        $http.get(contextPath + 'api/v1/productsAnalytics/topOfDay')
            .then(function (response) {
                console.log(response);
                $scope.TopDayProducts = response.data;
            });
    };

    $scope.updateStatistics = function () {
        $scope.loadTopMonthProducts();
        $scope.loadTopDayProducts();
    };

    $scope.runAnalise = function(){
        setInterval(function(){$scope.loadTopMonthProducts(); $scope.loadTopDayProducts()}, 60000);
    };

    $scope.updateStatistics();
    $scope.runAnalise();
});