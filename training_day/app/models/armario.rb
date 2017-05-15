class Armario < ApplicationRecord
  has_many :fotos, as: :imageable
end
