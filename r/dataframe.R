df <- data.frame(
  Name = c("Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"),
  Age = c(22, 35, 58),
  Sex = c("male", "male", "female")
)

df

names(df)

df[c("Age")]
df[c("Age", "Sex")]

df[2]
df[2:3]
df[c(2,3)]

df[df$Age > 30, ]
df[df$Age > 30 & df$Sex == "male", ]
