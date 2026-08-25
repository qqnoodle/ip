# UI test session

## 1. TC01: Exit immediately — FAIL

Aim: Verify that the application displays its greeting and a farewell when the user exits.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
bye
```

**Expected output:**
```text
____________________________________________________________
    _                       _
   / \   _ __ _ __ ___   __| | ___  ___
  / _ \ | '__| '__/ _ \ / _` |/ _ \/ __|
 / ___ \| |  | | | (_) | (_| |  __/\__ \
/_/   \_\_|  |_|  \___/ \__,_|\___||___/
Eyes that watch All living Beings
The Stigmata from the Primordial Land
The Great Arrodes is before you!
State your request!
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

**Actual output:**
```text
Error: Could not find or load main class Arrodes
Caused by: java.lang.ClassNotFoundException: Arrodes

```

Exit code: 1
