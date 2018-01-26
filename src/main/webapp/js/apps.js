var app = angular.module("cadUsuario", []);

app.value('urlBase', 'http://localhost:8080/sProdutos/rest/');

app.controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase){
    $scope.login = {id:"", nome:"", idade:"", endereco:"", telefone:"", usuario:"", senha: ""};
    $scope.cad = [{nome: "", idade:"", endereco:"", telefone:""}];
    
    $scope.logarUser = function(login) {
        console.log(login.nome);
        $http({method:'POST',
            url:urlBase + "acessologin", 
            data:login}).then(function (response) {
            console.log(response.data.toString());

            /*if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
                $window.location.href = 'exibicao.html';
            }*/
        });
    };
    
    $scope.enviadados = function(cad) {
        console.log("Chegou aqui;;;;");
        console.log(cad.nome);
        
        
    };
});


