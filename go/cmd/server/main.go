package main

import (
	"bookswitcher/internal/db"
	"bookswitcher/internal/proto"
	"context"
	"log"
	"os"
	"time"
)

func main() {
	dbCtx, timeout := context.WithTimeout(context.Background(), 5*time.Minute)
	defer timeout()

	log.Println("Connecting to the db...")
	err := db.InitDB(dbCtx, os.Getenv("DB_ADDRESS"))
	if err != nil {
		log.Fatal(err.Error())
	}

	err = db.InitTables(dbCtx)
	if err != nil {
		log.Fatal(err.Error())
	}

	log.Println("Starting an auth service...")
	err = proto.UserServerListen(":" + os.Getenv("AUTH_PORT"))
	if err != nil {
		log.Fatal(err.Error())
	}
}
