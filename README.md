# duct-boundary-testing

Webフレームワーク「Duct」入門サンプルコード、単体テスト boundary 編。

DB アクセス部分をテストしています。<br>
dev / test ごとにスキーマを別にした（そのような要望があるのかは分からないけど）。<br>
:duct.migrator/ragtime によって、dev / test それぞれのスキーマの初期データを整備している。<br>

ネットで検索したところ、<br>
:duct.migrator/ragtime を用いているサンプルは、<br>
どれも dev / test が同じスキーマを共有しているんじゃないかな、というものばかり。<br>

「migrator」の役割もよく分かっていないで作業したのだけれど、<br>
…とりあえず、試行錯誤して現状の動くやつをば。<br>

あと、.sql ファイルだけど、「--;;」でいちいち命令行を区切ってやらないとエラーが出るのね…。<br>
どハマりしてました。<br>

## Developing

<!---

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

-->

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests (with keywords) through Leiningen.

```edn
;; [  duct-boundary-testing/project.clj ]

(defproject duct-boundary-testing "0.1.0-SNAPSHOT"

 (中略)

 :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.2"]
                                   [hawk "0.2.11"]
                                   [eftest "0.5.9"]

                                   ;; add
                                   [phrase "0.3-alpha4"]]

                  ;; add2
                  ;; テストコードに追加したキーワードと一致させること。
                  ;; キーワードに該当するテストのみ、部分的に実行できる。
                  :test-selectors {:b :boundary}
                  }})
```

```sh
$ lein test :b
Java HotSpot(TM) 64-Bit Server VM warning:
Options -Xverify:none and -noverify were deprecated in JDK 13 and will likely be removed in a future release.

lein test duct-boundary-testing.boundary.users-test
Applying :duct-boundary-testing.migration/create-users-test#2f7a9edd

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.

```

```sh
$ lein migrate test
Java HotSpot(TM) 64-Bit Server VM warning: 
Options -Xverify:none and -noverify were deprecated in JDK 13 and will likely be removed in a future release.
Applying 01-create-users

$ lein rollback test
Java HotSpot(TM) 64-Bit Server VM warning: 
Options -Xverify:none and -noverify were deprecated in JDK 13 and will likely be removed in a future release.
Rolling back 01-create-users

```

<!---

## Legal

Copyright © 2021 FIXME

-->

## References

- 「[clojureのductでcrudアプリを作る方法][1]」 [ `https://asukiaaa.blogspot.com/2017/12/clojureductcrud.html` ]<br>
- 「[Clojure と Duct の単体テスト][2]」 [ `https://webcache.googleusercontent.com/search?q=cache:wyR4h6_n7VAJ:https://sfkd.hatenablog.com/+&cd=1&hl=ja&ct=clnk&gl=jp&client=firefox-b-d` ]<br>

[1]: https://asukiaaa.blogspot.com/2017/12/clojureductcrud.html
[2]: https://webcache.googleusercontent.com/search?q=cache:wyR4h6_n7VAJ:https://sfkd.hatenablog.com/+&cd=1&hl=ja&ct=clnk&gl=jp&client=firefox-b-d
