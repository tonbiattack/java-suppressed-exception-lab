# デバッグ記録

## 再現

コミット `63cda16` で `mvn --batch-mode test` を実行すると、`expected: <body failure; suppressed=close failure> but was: <body failure>` となる。

## 観測

`try` 本体で `body failure`、`close` で `close failure` が発生する。出力は `diagnostic=body failure` であり、テストは終了例外を期待して失敗する。

## 仮説比較

| 仮説 | 実験 | 結果 |
| --- | --- | --- |
| 本体処理だけが失敗した | `getSuppressed()` を確認する | suppressedに `close failure` が存在したため棄却 |
| close例外は本体例外に記録される | `Throwable#getSuppressed()` を確認する | 記録されており採用 |
| テストの期待値が過剰 | try-with-resourcesの仕様と実行値を比較する | 実際に2例外が存在するため棄却 |

## 原因

try-with-resourcesは本体例外を主例外として伝播し、終了時の例外をsuppressed例外としてThrowableへ記録する。`getMessage()` だけではsuppressed配列を読まないため、診断文字列から終了失敗が落ちた。[1]

## 最小修正

`exception.getSuppressed()` を走査して診断へ追記した。修正コミットは `90aa432` である。

## 再発防止テスト

元のテストを残し、`body failure; suppressed=close failure` を確認する。修正後は `Tests run: 1, Failures: 0, Errors: 0`、`BUILD SUCCESS` となる。

## References

[1] [Java SE 21 API — Throwable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html)
