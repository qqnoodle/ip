# UI test session

## 1. TC01: Exit immediately — PASS

Aim: Verify that the application displays its greeting and a farewell when the user exits.

**Command:**
```text
java -cp out\production\ip Arrodes
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

Exit code: 0
