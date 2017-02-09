class Movie < ApplicationRecord

  mount_uploader :poster, PosterUploader

end