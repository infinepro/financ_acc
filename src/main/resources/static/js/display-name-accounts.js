var app = angular.module("hello page", []);

app.controller("usersList", function ($scope, $http) {
    $scope.users = []

    $http.get("http://localhost:8080/get-my-users-list").then(function (successCallback) {
        $scope.users = successCallback.data;
    }, function (errorCallback) {
    });

});
