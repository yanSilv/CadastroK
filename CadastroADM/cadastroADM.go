package main

import (
	"fmt"
	"strings"
	"io/ioutil"
	_"github.com/go-sql-driver/mysql"
	"database/sql"
)

func dadosBanco (dados string) {
	
	linhas := strings.Split(string(dados), ";")
	
	nome := ""
	endereco := ""
	idade := ""
	telefone := ""
	usuario := ""
	senha := ""
	status := ""
	
	for i := 0; i < len(linhas); i++ {
		switch i {
			case 0:
				nome = linhas[i]
			case 1:
				endereco = linhas[i]
			case 2:
				idade =  linhas[i]
			case 3:
				telefone = linhas[i]
			case 4:
				usuario = linhas[i]
			case 5:
				senha = linhas[i]
			case 6:
				status = linhas[i] 
		}
	}
	
	fmt.Println(nome)
	fmt.Println(endereco)
	fmt.Println(idade)
	fmt.Println(telefone)
	fmt.Println(usuario)
	fmt.Println(senha)
	fmt.Println(status)
	
	//Abre a conexão com o banco de dados.
	db, err := sql.Open("mysql", "root:123456@tcp(127.0.0.1:3306)/usuarioDb")
	checkErr(err)
	
	defer db.Close()
	
	stmt, err := db.Prepare("INSERT INTO tb_usuario(nome, endereco, idade, telefone, usuario, senha, status) values (?, ?, ?, ?, ?, ?, ?)")
	checkErr(err)
	
	res, err := stmt.Exec(nome, endereco, idade, telefone, usuario, senha, status)
	checkErr(err)
	
	id, err := res.LastInsertId()
	checkErr(err)
	
	fmt.Println(id)
	
	
} 

func checkErr(err error) {
	if (err != nil) {
		panic(err.Error())
	}
}

func main() {
	contents, erro := ioutil.ReadFile("config.cfg")
	
	if (erro != nil) {
		fmt.Println(erro)
	}
	
	linhas := strings.Split(string(contents), "\n")
	
	if (erro == nil) {
		for i := 0; i < len(linhas); i++ {
			
			conteudo := linhas[i]
			
			if ( len(conteudo) == 0 ) {
				continue
			}
			
			caracter := string(conteudo[0:1]) 
			
			if (caracter == "#") {
				continue
			} 
			
			dadosBanco(conteudo)
			
		}
	}
}
