angular.module('market-front').controller('orderController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    }

    $scope.goToPay = function (orderId) {
        $location.path('/order_pay/' + orderId);
    }

    $scope.cancelOrder = function (orderId) {
        $http.get(contextPath + 'api/v1/orders/cancel/' + orderId)
            .then(function (response) {
                $scope.loadOrders();
            });
    }
    $scope.loadOrders();
});