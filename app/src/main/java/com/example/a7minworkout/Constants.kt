package com.example.a7minworkout

object Constants {
     fun defultExcersiseList(): ArrayList<Excersises>{
         val excersiseList = ArrayList<Excersises>()
         val jumpingJacks = Excersises(
             1,"jumping jacks", R.drawable.workout, false,false
         )
         excersiseList.add(jumpingJacks)
         val wallSit = Excersises(2,"wallsit", R.drawable.workout2,false,false)
         excersiseList.add(wallSit)

         val pushUp = Excersises(3, "push up", R.drawable.start_image, false,false)
         excersiseList.add(pushUp)

         val abdominalCrunch = Excersises(4,"abscrunches", R.drawable.abdominal_crunches,false, false)
         excersiseList.add(abdominalCrunch)

         val sitUpOnchair = Excersises(5,"chair sit up", R.drawable.chair_situp,false, false)
         excersiseList.add(sitUpOnchair)

         val squat = Excersises(6,"squat", R.drawable.squats,false, false)
         excersiseList.add(squat)

         val tricepsDip = Excersises(7,"tricep dip", R.drawable.tricep_dip,false, false)
         excersiseList.add(tricepsDip)

         val plank = Excersises(8,"planks", R.drawable.planks,false, false)
         excersiseList.add(plank)

         val highKneeRunningInPlace = Excersises(9,"running in place", R.drawable.high_knee_running,false, false)
         excersiseList.add(highKneeRunningInPlace)

         val lunges = Excersises(10,"lunges", R.drawable.lunges,false, false)
         excersiseList.add(lunges)

         val pushUpAndRotation = Excersises(11,"push up and rotation", R.drawable.push_up_rotations,false, false)
         excersiseList.add(pushUpAndRotation)

         val sidePlank = Excersises(12,"sidePlank", R.drawable.side_planks,false, false)
         excersiseList.add(sidePlank)
         return excersiseList
     }
}