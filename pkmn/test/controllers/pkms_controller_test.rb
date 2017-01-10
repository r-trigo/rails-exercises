require 'test_helper'

class PkmsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @pkm = pkms(:one)
  end

  test "should get index" do
    get pkms_url
    assert_response :success
  end

  test "should get new" do
    get new_pkm_url
    assert_response :success
  end

  test "should create pkm" do
    assert_difference('Pkm.count') do
      post pkms_url, params: { pkm: { comp_id: @pkm.comp_id, name: @pkm.name } }
    end

    assert_redirected_to pkm_url(Pkm.last)
  end

  test "should show pkm" do
    get pkm_url(@pkm)
    assert_response :success
  end

  test "should get edit" do
    get edit_pkm_url(@pkm)
    assert_response :success
  end

  test "should update pkm" do
    patch pkm_url(@pkm), params: { pkm: { comp_id: @pkm.comp_id, name: @pkm.name } }
    assert_redirected_to pkm_url(@pkm)
  end

  test "should destroy pkm" do
    assert_difference('Pkm.count', -1) do
      delete pkm_url(@pkm)
    end

    assert_redirected_to pkms_url
  end
end
