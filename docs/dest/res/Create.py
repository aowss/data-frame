import pandas as pd # <1>

df = pd.DataFrame( # <2>
   {
      "Name": ["Braund, Mr. Owen Harris", "Allen, Mr. William Henry", "Bonnell, Miss. Elizabeth"],
      "Age": [22, 35, 58],
      "Sex": ["male", "male", "female"]
   }
)