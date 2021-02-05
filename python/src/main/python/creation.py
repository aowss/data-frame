import pandas as pd

df = pd.DataFrame(
   {
      "Name": ["Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"],
      "Age": [22, 35, 58],
      "Sex": ["male", "male", "female"]
   }
)

print("\nDataFrame: \n" + str(df))

print("\nDataFrame's describe(): \n" + str(df.describe()))

ages = pd.Series([22, 35, 58], name="Age")

print("\nSeries: \n" + str(ages))

print("\nSeries' describe(): \n" + str(ages.describe()))

print("\nDataFrame's 'Age' column: \n" + str(df["Age"]))

print("\nDataFrame's 'Age' and 'Sex' columns: \n" + str(df[["Age", "Sex"]]))
