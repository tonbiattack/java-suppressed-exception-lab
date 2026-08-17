# Javaのtry-with-resourcesでsuppressed例外を見落とす

本ラボは、try-with-resourcesで本体例外とリソース終了例外が同時に発生したとき、`getMessage()` だけを読むと終了時の失敗を失う問題を再現します。

## 実行

```bash
mvn --batch-mode test
```

バグ状態は `63cda16` で、期待する `body failure; suppressed=close failure` に対して `body failure` となります。修正状態は `90aa432` で、`Throwable#getSuppressed()` を含めて診断し、同じテストが成功します。

## 学習の流れ

| 段階 | 観測 |
| --- | --- |
| 再現 | 本体例外だけが表示される |
| 仮説 | close例外が消えた、またはテストが誤っている |
| 切り分け | `getSuppressed()` の内容を確認する |
| 修正 | suppressed例外を診断へ追加する |

## 構成

`SuppressedDiagnostic` が公開境界で、`SuppressedDiagnosticTest` が本体例外と終了例外を独立に確認する契約テストです。詳細は `docs/debugging-record.md` を参照してください。

## References

[1] [Java SE 21 API — Throwable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html)
