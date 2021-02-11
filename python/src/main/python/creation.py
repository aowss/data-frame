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

print("\nDataFrame's column names: \n" + str(df.columns))

print("\nDataFrame's 'Age' column: \n" + str(df["Age"]))

print("\nDataFrame's 'Age' and 'Sex' columns: \n" + str(df[["Age", "Sex"]]))

print("\nDataFrame's 'Age' column by index: \n" + str(df.iloc[:, 1]))

print("\nDataFrame's 'Age' and 'Sex' columns by indices: \n" + str(df.iloc[:, [1, 2]]))

print("\nFilter rows where Age > 30: \n" + str(df[df["Age"] > 30]))

print("\nFilter rows where Age > 30 and Sex == male : \n" + str(df[(df["Age"] > 30) & (df["Sex"] == "male")]))

