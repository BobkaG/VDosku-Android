package com.example.testproj

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timetable.Lesson
import com.example.timetable.R
import com.mrerror.singleRowCalendar.SingleRowCalendar
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue600
import com.mrerror.singleRowCalendar.SingleRowCalendarDefaults.Blue601

@Composable
fun LessonCard(
    key: Lesson,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 5.dp, horizontal = 20.dp)
            .shadow(elevation = 10.dp)

        ,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            // Время пары
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = key.start,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,
                    color = Color.Black,
                )
                Image(
                    painter = painterResource(id = R.drawable.timeicon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp).drawBehind {


                            drawLine(color = Blue600,this.size.center.minus(
                                Offset(-70f,200f)
                            ),this.size.center.plus(
                                Offset(70f,200f)
                            ), strokeWidth = 3f)
                        },
                )
/*                                                Text(
                                                    text = "",
                                                    fontSize = 13.sp,
                                                    color = Color.Black,
                                                    modifier = Modifier
                                                        .align(Alignment.Start)
                                                        .padding(horizontal = 15.dp)
                                                        .drawBehind {
                                                            drawCircle(
                                                                color = Blue600, radius = 25f
                                                            )


                                                            drawLine(color = Blue600,this.size.center.minus(
                                                                Offset(0f,200f)
                                                            ),this.size.center.plus(
                                                                Offset(0f,200f)
                                                            ), strokeWidth = 2f)
                                                        }
                                                )*/
                Text(
                    text = key.end,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            // Описание пары
            Column(
                modifier = Modifier
                    .fillMaxSize().padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
/*                    Image(
                        painter = painterResource(id = R.drawable.lesson),
                        contentDescription = null,
                        modifier = Modifier
                            .size(13.dp),
                    )*/
                    Text(
                        text = key.name,//lesson.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(1000),
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }



                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
/*                    Image(
                        painter = painterResource(id = R.drawable.prepod1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 18.dp, height = 18.dp),
                        contentScale = ContentScale.Crop
                    )*/

                    Text(
                        text = key.teacher,//lesson.teachers[0].name,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 11.sp,
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
/*                    Image(
                        painter = painterResource(id = R.drawable.auditory),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp),
                    )*/
                    Text(
                        text = key.auditory,//lesson.classroom,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 11.sp,
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }



            }

        }
    }



}
