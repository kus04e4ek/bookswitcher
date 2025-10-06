package auth

import (
	"testing"
)

func TestGetKey(t *testing.T) {
	tests := []struct {
		key string
	}{
		{"alpha"},
		{"12345"},
		{"saddas(%)^,mmm./,al,a;l,'paw"},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			t.Setenv("SECRET_KEY", test.key)
			result, err := getKey(nil)
			if string(result.([]byte)) != test.key || err != nil {
				t.Errorf("getKey(nil) = %s; expected %s", result, test.key)
			}
		})
	}
}

func TestEncodeDecode(t *testing.T) {
	keys := []struct {
		key string
	}{
		{"alpha"},
		{"12345"},
		{"saddas(%)^,mmm./,al,a;l,'paw"},
	}

	users := []struct {
		user User
	}{
		{User{}},
		{User{Id: 120, Username: "User 0", City: "City 0", IsAdmin: false}},
		{User{Id: 1312332, Username: "123321132", City: "asdsadadsasd", IsAdmin: true}},
	}

	for _, key := range keys {
		for _, user := range users {
			t.Run("", func(t *testing.T) {
				t.Setenv("SECRET_KEY", key.key)
				token, err1 := GetToken(user.user)
				result, err2 := GetUser(token)
				if *result != user.user || err1 != nil || err2 != nil {
					t.Errorf("SECRET_KEY=%s\nGetToken(%v) = %s, %v\nGetUser(%s) = %v, %v; expected %v", key.key, user.user, token, err1, token, err2, *result, user.user)
				}
			})
		}
	}
}
