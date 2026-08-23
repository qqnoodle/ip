# UI test session

## 1. TC01: Exit immediately — PASS

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

## 2. TC04: Recover from a corrupted data file — PASS

Aim: Verify that a corrupted data file is reported and the chatbot continues with an empty task list.

**Command:**
```text
powershell -NoProfile -Command "Set-Content data\arrodes.txt 'X | 0 | corrupt'; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
list
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
Arrodes could not load your requests.
____________________________________________________________
Arrodes recalls your requests:
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
Arrodes could not load your requests.
____________________________________________________________
Arrodes recalls your requests:
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

Exit code: 0

## 3. TC02: Add and list every task type — PASS

Aim: Verify that todo, deadline, event, and list commands display their stored tasks.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
todo buy milk
deadline submit report /by 2026-08-30
event team meeting /from 2026-08-30T14:00 /to 2026-08-30T15:00
list
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [D][ ] submit report (by: Aug 30 2026)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
3 tasks are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
2.[D][ ] submit report (by: Aug 30 2026)
3.[E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [D][ ] submit report (by: Aug 30 2026)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
3 tasks are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
2.[D][ ] submit report (by: Aug 30 2026)
3.[E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

Exit code: 0

## 4. TC03: Mark, unmark, and delete tasks — PASS

Aim: Verify that task status changes and deletion update the task list.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
todo buy milk
event team meeting /from 2026-08-30T14:00 /to 2026-08-30T15:00
mark 1
unmark 1
delete 2
list
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
A worthy task! Arrodes has marked it as done:
  [T][X] buy milk
____________________________________________________________
____________________________________________________________
As you decree, Arrodes has marked this task as not done yet:
  [T][ ] buy milk
____________________________________________________________
____________________________________________________________
Erasing records of the task:

[E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)

1 tasks remaining are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
A worthy task! Arrodes has marked it as done:
  [T][X] buy milk
____________________________________________________________
____________________________________________________________
As you decree, Arrodes has marked this task as not done yet:
  [T][ ] buy milk
____________________________________________________________
____________________________________________________________
Erasing records of the task:

[E][ ] team meeting (from: Aug 30 2026 14:00 to Aug 30 2026 15:00)

1 tasks remaining are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

Exit code: 0

## 5. TC05: Reject impossible calendar dates — PASS

Aim: Verify that a correctly shaped but impossible date, such as February 31, is rejected clearly.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
deadline submit report /by 2026-02-31
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
That date is invalid because the specified day or time does not exist.
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
That date is invalid because the specified day or time does not exist.
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

Exit code: 0

## 6. TC06: Validate event time ordering — PASS

Aim: Verify that same-day date-only events are accepted and reversed event times produce a warning.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
```text
event same day /from 2026-10-15 /to 2026-10-15
event reversed /from 2026-10-15T10:00 /to 2026-10-15T09:00
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
Inscribing request: 
   [E][ ] same day (from: Oct 15 2026 00:00 to Oct 15 2026 00:00)
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Event end time must not be earlier than its start time.
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
Inscribing request: 
   [E][ ] same day (from: Oct 15 2026 00:00 to Oct 15 2026 00:00)
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Event end time must not be earlier than its start time.
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________

```

Exit code: 0
